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
        //timeHandler.printBuckets();
/*
        eventsFile.loadFile();
        List<String> fileLines = eventsFile.getFileLines();
        System.out.println("All from file:");
        fileLines.forEach(System.out::println);
        String currentHandlingHour = null;
        long numOfSamples = 0;
        double sumOfTimes = 0;
        double average = 0;
        System.out.println(" calculation and results:");
        for (String line : fileLines) {
            String[] values = line.split(" ");
            String nearestHour = timeHandler.getNearestHour(values[0] + " " + values[1]).toString();
            double dvalue = Double.parseDouble(values[2]);
            if (currentHandlingHour == null) {
                currentHandlingHour = nearestHour;
            }
            if (!currentHandlingHour.equals(nearestHour)) { // nearestHour has changed.
                if (numOfSamples != 0) {
                    // compute
                    average = sumOfTimes / numOfSamples;
                }
                // reset
                System.out.println("saving: " + currentHandlingHour + " average:" + average);
                currentHandlingHour = nearestHour;
                numOfSamples = 0;
                sumOfTimes = 0;

            } else {
                sumOfTimes += dvalue;
                numOfSamples++;
                System.out.println("adding: " + dvalue + " result:" + sumOfTimes + " currentHandlingHour:" + currentHandlingHour);
            }

        }
        if (numOfSamples != 0) {
            // compute
            average = sumOfTimes / numOfSamples;
        }

        System.out.println("saving: " + currentHandlingHour + " average:" + average);
*/

    }

}

