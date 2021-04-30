package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    final EventsFile eventsFile;
    TimeHandler timeHandler;
    private static Logger LOGGER = LogManager.getLogger(DemoApplication.class);

    public DemoApplication(EventsFile eventsFile, TimeHandler timeHandler) {
        this.eventsFile = eventsFile;
        this.timeHandler = timeHandler;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        timeHandler.processData();
    }

}

