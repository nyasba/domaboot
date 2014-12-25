package com.example.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        return "hello, spring-boot world!";
    }
}
