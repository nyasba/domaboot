package com.example.web.selenide.page;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;

/**
 * メインページ
 */
public class MainPage {

    private static final String URL = "http://localhost:8888/customers";

    public static void open(){
        Selenide.open(URL);
    }

    public static String title(){
        return Selenide.title();
    }

    public static int 登録件数(){
        return Selenide.$$(Selenide.$(By.id("customer-list")).find("tbody"),"tr").size();
    }

    public static void 姓は(String lastName){
        Selenide.$(By.id("lastName")).setValue(lastName);
    }
    public static void 名は(String firstName){
        Selenide.$(By.id("firstName")).setValue(firstName);
    }

    public static void で登録する(){
        Selenide.$(By.id("register")).click();
    }

    public static String 名前(int index){
        return Selenide.$(By.id("lastName" + String.valueOf(index))).getText()
                +  Selenide.$(By.id("firstName" + String.valueOf(index))).getText();
    }

    public static void 編集ボタンを押す(int index){
        Selenide.$(By.id("edit" + String.valueOf(index))).click();
    }

    public static void 削除ボタンを押す(int index){
        Selenide.$(By.id("delete" + String.valueOf(index))).click();
    }

}
