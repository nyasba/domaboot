package com.example.api;

import com.example.App;
import com.example.datasource.CustomerRepository;
import com.example.domain.CustomerEntity;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.EncoderConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.http.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * CustomerRestControllerのテスト(RestAssured版)
 */
@RunWith(SpringJUnit4ClassRunner.class)                 // JUnitでSpringの機能を使うための記述
@SpringApplicationConfiguration(classes = App.class)    // EnableAutoConfigurationがついたクラスを指定。ApplicationContextを作る
@WebAppConfiguration                                     // Webアプリケーションのテストであることを記述
@IntegrationTest({
        "server.port:0",                              // 結合テスト機能を有効にする。port0で空いてるポートを利用
        "spring.profiles.active:test"               // テスト用の設定にする
})
public class CustomerRestControllerIntegrationTest {

    @Autowired
    CustomerRepository customerRepository;

    @Value("${local.server.port:0}")
    int port;

    CustomerEntity customer1 = new CustomerEntity(101, "どら", "えもん");
    CustomerEntity customer2 = new CustomerEntity(102, "野比", "のびた");

    @Before
    public void setUp() {
        customerRepository.deleteAll();
        customerRepository.insert(customer1);
        customerRepository.insert(customer2);
        RestAssured.port = port;
    }

    @After
    public void cleanUpClass() {
        customerRepository.deleteAll();
    }

    @Test
    public void 一覧取得を行う() {
        when()
                .get("/api/customers")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id[0]", is(customer1.getId()))
                .body("lastName[0]", is(customer1.getLastName()))
                .body("firstName[0]", is(customer1.getFirstName()))
                .body("id[1]", is(customer2.getId()))
                .body("lastName[1]", is(customer2.getLastName()))
                .body("firstName[1]", is(customer2.getFirstName()));
    }

    @Test
    public void 一覧取得を行う_1ページ1件で2ページ目() {
        given()
                .param("pageNumber", 2)
                .param("numberOfRecord", 1)
                .when()
                .get("/api/customers")
                .then()
                .statusCode(HttpStatus.OK.value())
                        // Listが返却されるので[0]が必要
                .body("id[0]", is(customer2.getId()))
                .body("lastName[0]", is(customer2.getLastName()))
                .body("firstName[0]", is(customer2.getFirstName()));
    }

    @Test
    public void ID指定取得を行う() {
        when()
                .get("/api/customers/{id}", 101)
                .then()
                .statusCode(HttpStatus.OK.value())
                        // Entityが返却されるので[0]は不要
                .body("id", is(customer1.getId()))
                .body("lastName", is(customer1.getLastName()))
                .body("firstName", is(customer1.getFirstName()));
    }

    @Test
    public void 新規登録を行う() {
        CustomerEntity SIZUKA_CHAN = new CustomerEntity(1, "宮本", "しずか");

        given()
                .contentType(ContentType.JSON)
                .config(getUTF8Config())
                .body(String.format("{ \"lastName\":\"%s\", \"firstName\":\"%s\" }", SIZUKA_CHAN.getLastName(), SIZUKA_CHAN.getFirstName()))
                .when()
                .post("/api/customers")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        assertThat(customerRepository.findById(1), is(SIZUKA_CHAN));
    }

    @Test
    public void 更新を行う() {
        CustomerEntity DORAMI = new CustomerEntity(101, "どら", "ミ");

        given()
                .contentType(ContentType.JSON)
                .config(getUTF8Config())
                .body(String.format("{ \"lastName\":\"%s\", \"firstName\":\"%s\" }", DORAMI.getLastName(), DORAMI.getFirstName()))
                .when()
                .put("/api/customers/{id}", 101)
                .then()
                .statusCode(HttpStatus.OK.value());

        assertThat(customerRepository.findById(101), is(DORAMI));
    }

    @Test
    public void 削除を行う() {
        when()
                .delete("/api/customers/{id}", 101)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        assertThat(customerRepository.findById(101), is(nullValue()));
    }

    private RestAssuredConfig getUTF8Config() {
        return new RestAssuredConfig().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8"));
    }

}
