package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

@Component
public class WatchFile {
    private static final Logger LOGGER = LogManager.getLogger(WatchFile.class);
    TimeHandler timeHandler;
    String directoryName;

    public WatchFile(TimeHandler timeHandler, String directoryName) {
        this.timeHandler = timeHandler;
        this.directoryName = directoryName;

        Thread thread = new Thread(this::doWatch);
        thread.start();
    }

    private void doWatch() {
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path logDir = Paths.get(directoryName);
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
