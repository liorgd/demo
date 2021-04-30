package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class TimeHandler {

    private static final Logger LOGGER = LogManager.getLogger(TimeHandler.class);

    @Autowired
    EventsFile eventsFile;

    public List<Bucket> getBuckets() {
        return buckets;
    }

    List<Bucket> buckets = new ArrayList<>();

    private LocalDateTime getNearestHour(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime datetime = LocalDateTime.parse(time, formatter);
        int minutes = datetime.getMinute();
        int mod = minutes % 5;
        LocalDateTime newDatetime;
        newDatetime = datetime.minusMinutes(mod);
        newDatetime = newDatetime.truncatedTo(ChronoUnit.MINUTES);
        return newDatetime;
    }

    public void processData() {
        buckets.clear();
        List<String> fileLines = eventsFile.loadFile();
        LOGGER.info("All from file:");
        fileLines.forEach(LOGGER::debug);
        String currentHandlingHour = null;

        List<Double> timesPerBucket = new ArrayList<>();
        LOGGER.info(" calculation and results:");
        for (String line : fileLines) {
            String[] values = line.split(" ");
            String nearestHour = getNearestHour(values[0] + " " + values[1]).toString();
            double dvalue = Double.parseDouble(values[2]);

            if (currentHandlingHour == null) {
                currentHandlingHour = nearestHour;
            }

            if (currentHandlingHour.equals(nearestHour)) {
                timesPerBucket.add(dvalue);
            }

            if (!currentHandlingHour.equals(nearestHour)) {
                Double sumForBucket = timesPerBucket.stream().mapToDouble(Double::doubleValue).sum();
                double average = sumForBucket / timesPerBucket.size();
                timesPerBucket.clear();
                LOGGER.info("saving: " + currentHandlingHour + " average:" + average);
                buckets.add(new Bucket(currentHandlingHour, average));
                currentHandlingHour = nearestHour;
                timesPerBucket.add(dvalue);
            }

        }

        // compute the last bucket here:
        double sumForBucket = timesPerBucket.stream().mapToDouble(Double::doubleValue).sum();
        double average = sumForBucket / timesPerBucket.size();
        timesPerBucket.clear();
        LOGGER.info("saving: " + currentHandlingHour + " average:" + average);
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
