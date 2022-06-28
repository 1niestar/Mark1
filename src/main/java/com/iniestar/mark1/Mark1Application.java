package com.iniestar.mark1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.iniestar"})
public class Mark1Application {

    public static void main(String[] args) {
        SpringApplication.run(Mark1Application.class, args);
    }

}
