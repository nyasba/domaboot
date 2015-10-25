package com.example.web.selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.example.App;
import com.example.web.selenide.page.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CustomerWebTest {

    @Before
    public void setUp() {

        // テスト対象アプリの起動
        App.main(new String[]{
                "--server.port=8888",
                "--spring.profiles.active=test"
        });

        // 何も指定しない場合はFirefoxになる
        Configuration.browser = WebDriverRunner.CHROME;
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
    }

    @After
    public void tearDown() {
        WebDriverRunner.closeWebDriver();
    }

    @Test
    public void 剛田たけしを登録する() {
        MainPage.open();
        assertThat(MainPage.登録件数(), is(0));

        MainPage.姓は("剛田");
        MainPage.名は("たけし");
        MainPage.で登録する();

        assertThat(MainPage.登録件数(), is(1));
        assertThat(MainPage.名前(1), is("剛田たけし"));
    }
}
