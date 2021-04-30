package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

@Component
public class WatchFile {
    private static Logger LOGGER = LogManager.getLogger(WatchFile.class);
    TimeHandler timeHandler;

    public WatchFile(TimeHandler timeHandler) {
        this.timeHandler = timeHandler;

        Thread thread = new Thread(() -> doWatch());
        thread.start();
    }

    private void doWatch() {
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path logDir = Paths.get("C:\\Programs\\demo2\\demo\\src\\main\\resources");
            logDir.register(watcher, ENTRY_MODIFY);

            while (true) {
                WatchKey key = watcher.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    if (ENTRY_MODIFY.equals(kind)) {
                        LOGGER.info("File might be modified");
                        timeHandler.processData();
                    }
                }
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
