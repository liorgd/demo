package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventsFile {

    String fileName;

    @Autowired
    public EventsFile(String fileName) {
        this.fileName = fileName;
    }

    List<String> loadFile() {
        List<String> fileLines = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();

        try (InputStream inputStream = new FileInputStream(new File(fileName));
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                fileLines.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileLines;
    }
}
