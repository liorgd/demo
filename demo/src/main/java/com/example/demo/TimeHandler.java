package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class TimeHandler {
    @Autowired
    EventsFile eventsFile;

    public List<Bucket> getBuckets() {
        return buckets;
    }

    public void setBuckets(List<Bucket> buckets) {
        this.buckets = buckets;
    }

    List<Bucket> buckets = new ArrayList<>();

    public void printBuckets() {
        System.out.println(buckets);
    }

    private LocalDateTime getNearestHour(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime datetime = LocalDateTime.parse(time, formatter);
        int minutes = datetime.getMinute();
        int mod = minutes % 5;
        LocalDateTime newDatetime;
        newDatetime = datetime.minusMinutes(mod);
        newDatetime = newDatetime.truncatedTo(ChronoUnit.MINUTES);

        //System.out.println("getNearestHour: " + time + " " + newDatetime);
        return newDatetime;
    }

    public void processData() {
        buckets.clear();
        List<String> fileLines = eventsFile.loadFile();
        System.out.println("All from file:");
        fileLines.forEach(System.out::println);
        String currentHandlingHour = null;
        long numOfSamples = 0;
        double sumOfTimes = 0;
        double average = 0;
        System.out.println(" calculation and results:");
        for (String line : fileLines) {
            String[] values = line.split(" ");
            String nearestHour = getNearestHour(values[0] + " " + values[1]).toString();
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
                buckets.add(new Bucket(currentHandlingHour, average));
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
        buckets.add(new Bucket(currentHandlingHour, average));
    }

    static class Bucket {
        String time;
        Double average;

        public Bucket(String time, Double average) {
            this.time = time;
            this.average = average;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Double getAverage() {
            return average;
        }

        public void setAverage(Double average) {
            this.average = average;
        }

        @Override
        public String toString() {
            return new org.apache.commons.lang3.builder.ToStringBuilder(this)
                    .append("time", time)
                    .append("average", average)
                    .toString();
        }
    }
}