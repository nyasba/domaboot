package com.example.web.selenide.page;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;

/**
 * Created by ny on 2015/10/25.
 */
public class MainPage {

    private static final String URL = "http://localhost:8888/customers";

    public static void open(){
        Selenide.open(URL);
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
}
