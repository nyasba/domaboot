package com.example.web.selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.example.App;
import com.example.web.selenide.page.MainPage;
import lombok.AllArgsConstructor;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@FixMethodOrder
@RunWith(Theories.class)
public class CustomerWebTest {

    @BeforeClass
    public static void setUpClass() {

        // テスト対象アプリの起動
        App.main(new String[]{
                "--server.port=8888",
                "--spring.profiles.active=test"
        });

        // 何も指定しない場合はFirefoxになる
        Configuration.browser = WebDriverRunner.CHROME;
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
    }

    @AfterClass
    public static void tearDownClass() {
        WebDriverRunner.closeWebDriver();
    }

    @Theory
    public void 名前を登録する(Fixture fixture) {
        MainPage.open();
        assertThat(MainPage.登録件数(), is(fixture.num - 1));

        MainPage.姓は(fixture.lastName);
        MainPage.名は(fixture.firstName);
        MainPage.で登録する();

        assertThat(MainPage.登録件数(), is(fixture.num));
        assertThat(MainPage.名前(fixture.num), is(fixture.lastName + fixture.firstName));
    }

    @DataPoints
    public static Fixture[] fixtures = new Fixture[]{
            new Fixture(1, "剛田", "たけし"),
            new Fixture(2, "どら", "えもん")
    };

    @AllArgsConstructor
    static class Fixture {
        int num;
        String lastName;
        String firstName;
    }

}
