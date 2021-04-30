package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TimeController {

    @Autowired
    TimeHandler timeHandler;

    @GetMapping("/timestamps")
    public List<TimeHandler.Bucket> getSampledTimestamps() {
        return timeHandler.getBuckets();
    }

    @GetMapping("/refresh")
    public void refresh() {
        timeHandler.processData();
    }
}
