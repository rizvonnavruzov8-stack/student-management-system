package com.sms.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 🎓 EXPLANATION: Logger (System Monitoring)
 * 
 * 1. WHY IT EXISTS:
 *    To keep a history of what happened in the system (e.g., errors, logins).
 * 
 * 2. LOGIC:
 *    - It appends messages to a 'logs.txt' file.
 *    - It adds a 'TIMESTAMP' to every message so we know WHEN it happened.
 */
public class Logger {

    private static final String LOG_FILE = "logs.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Logs a message with a timestamp.
     * @param message the content to log
     */
    public static void log(String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            bw.write("[" + timestamp + "] " + message);
            bw.newLine();
        } catch (IOException e) {
            // If logging fails, we print to console as a last resort
            System.err.println("Logging failed: " + e.getMessage());
        }
    }
}
