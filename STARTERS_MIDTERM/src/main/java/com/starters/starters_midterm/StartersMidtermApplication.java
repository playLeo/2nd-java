package com.starters.starters_midterm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class StartersMidtermApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartersMidtermApplication.class, args);
    }

}
