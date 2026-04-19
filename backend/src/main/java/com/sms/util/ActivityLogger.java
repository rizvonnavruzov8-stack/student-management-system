package com.sms.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LOGGING — writes timestamped activity entries to activity_log.txt.
 */
@Component
public class ActivityLogger {

    @Value("${app.log-file}") private String logFile;

    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void log(String action, String detail) {
        String entry = "[" + LocalDateTime.now().format(    FORMATTER) + "] "
                       + action + " — " + detail;
        System.out.println(entry);

        try {
            Files.createDirectories(Paths.get(logFile).getParent());
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
                bw.write(entry);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("[Logger] Could not write log: " + e.getMessage());
        }
    }
}
