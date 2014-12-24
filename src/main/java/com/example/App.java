package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * SpringBoot起動用クラス
 */
@EnableAutoConfiguration
@ComponentScan
public class App {

    public static void main(String[] args){
        SpringApplication.run(App.class, args);
    }
}
