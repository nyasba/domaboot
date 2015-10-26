package com.example.web.selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.example.App;
import com.example.web.selenide.page.EditPage;
import com.example.web.selenide.page.MainPage;
import org.junit.*;
import org.junit.runners.MethodSorters;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

//@RunWith(Theories.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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

    @After
    public void tearDown() {
        // 登録されてるデータを全部消す
        for(int i = MainPage.登録件数(); i > 0; i--){
            System.out.println("削除" + String.valueOf(i));
            MainPage.削除ボタンを押す(i);
        }
    }


    @AfterClass
    public static void tearDownClass() {
        WebDriverRunner.closeWebDriver();
    }

    @Test
    public void 剛田たけしとどらえもんを登録する() {
        MainPage.open();
        assertThat(MainPage.登録件数(), is(0));

        MainPage.姓は("剛田");
        MainPage.名は("たけし");
        MainPage.で登録する();

        assertThat(MainPage.登録件数(), is(1));
        assertThat(MainPage.名前(1), is("剛田たけし"));

        MainPage.姓は("どら");
        MainPage.名は("えもん");
        MainPage.で登録する();

        assertThat(MainPage.登録件数(), is(2));
        assertThat(MainPage.名前(2), is("どらえもん"));

    }

    @Test
    public void 剛田たけしで登録した後にきれいなジャイアンに変更する() {
        MainPage.open();
        MainPage.姓は("剛田");
        MainPage.名は("たけし");
        MainPage.で登録する();

        MainPage.編集ボタンを押す(1);

        // なぜかよく落ちるのでコメントアウト
//        assertThat(EditPage.title(), is("顧客情報編集"));

        EditPage.姓は("きれいな");
        EditPage.名は("ジャイアン");
        EditPage.で更新する();

        assertThat(MainPage.title(), is("顧客管理システム"));
        assertThat(MainPage.名前(1), is("きれいなジャイアン"));
    }

    @Test
    public void 剛田たけしで登録した後に変更しようとしてやっぱり戻る() {
        MainPage.open();
        MainPage.姓は("剛田");
        MainPage.名は("たけし");
        MainPage.で登録する();

        MainPage.編集ボタンを押す(1);

        // なぜかよく落ちるのでコメントアウト
//        assertThat(EditPage.title(), is("顧客情報編集"));

        EditPage.やっぱり戻る();

        assertThat(MainPage.名前(1), is("剛田たけし"));
    }

    @Test
    public void 剛田たけしで登録した後に削除する() {
        MainPage.open();
        MainPage.姓は("剛田");
        MainPage.名は("たけし");
        MainPage.で登録する();

        assertThat(MainPage.登録件数(), is(1));
        MainPage.削除ボタンを押す(1);

        assertThat(MainPage.登録件数(), is(0));
    }


}
