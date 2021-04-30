package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class Config {
    final private String FILE_DIRECTORY = "C:\\Programs\\demo2\\data";
    final private String FILE_PATH = FILE_DIRECTORY + "\\eventsFile.csv";

    @Bean
    public String fileName() {
        return FILE_PATH;
    }

    @Bean
    public String directoryName() {
        return FILE_DIRECTORY;
    }

}
