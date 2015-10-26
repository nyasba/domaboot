package com.example.web.selenide.page;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;

/**
 * 編集ページ
 */
public class EditPage {

    public static String title(){
        return Selenide.title();
    }

    public static void 姓は(String lastName){
        Selenide.$(By.id("lastName")).setValue(lastName);
    }
    public static void 名は(String firstName){
        Selenide.$(By.id("firstName")).setValue(firstName);
    }

    public static void で更新する(){
        Selenide.$(By.id("submit")).click();
    }

    public static void やっぱり戻る(){
        Selenide.$(By.id("goToTop")).click();
    }
}
