package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class Config {
    @Bean
    public String fileName() {
        return "C:\\Programs\\demo2\\data\\eventsFile.csv";
    }
}
