package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * HelloWorldのテスト
 */
@RunWith(SpringJUnit4ClassRunner.class)                 // JUnitでSpringの機能を使うための記述
@SpringApplicationConfiguration(classes = App.class)    // EnableAutoConfigurationがついたクラスを指定。ApplicationContextを作る
@WebAppConfiguration                                     // Webアプリケーションのテストであることを記述
@IntegrationTest({
        "server.port:0",                              // 結合テスト機能を有効にする。port0で空いてるポートを利用
        "spring.profiles.active:test"               // テスト用の設定にする
})
public class AppTest {

    @Value("${local.server.port}")
    int port;

    RestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testHome() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/hello", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is("hello, spring-boot world!"));
    }
}
