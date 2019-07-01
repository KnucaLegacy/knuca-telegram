package com.theopus.schedule.rest;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.theopus.schedule.rest.configuration")
public class Test {

    public static void main(String[] args) {
        SpringApplication.run(Test.class, args);
    }
}
