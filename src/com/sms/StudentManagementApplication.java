package com.sms;

import com.sms.util.FileManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

/**
 * Application Entry Point.
 * Starts the Spring Boot application and loads persisted data from files.
 */
@SpringBootApplication
public class StudentManagementApplication {

    public static void main(String[] args) throws IOException {
        ApplicationContext ctx = SpringApplication.run(StudentManagementApplication.class, args);

        // Load all persisted data from flat files on startup
        FileManager fileManager = ctx.getBean(FileManager.class);
        fileManager.loadAll();

        System.out.println("✅ Student Management System started at http://localhost:8080");
    }
}
