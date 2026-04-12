package com.sms;

import com.sms.ui.Menu;
import com.sms.util.Logger;

/**
 * 🎓 EXPLANATION: Main (Program Entry Point)
 * 
 * 1. WHY IT EXISTS:
 *    Every Java program needs a 'main' method as a starting point.
 * 
 * 2. PROGRAM LIFECYCLE:
 *    - Start: Initialize Logging.
 *    - Run: Create and display the Menu.
 *    - End: Menu handles saving and exit.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("--cli")) {
            Logger.log("System starting up (CLI)...");
            System.out.println("Initializing Student Management System (CLI)...");
            Menu menu = new Menu();
            menu.start();
        } else {
            Logger.log("System starting up (GUI)...");
            System.out.println("Initializing Student Management System (GUI)...");
            javax.swing.SwingUtilities.invokeLater(() -> {
                new com.sms.ui.StudentManagementGUI().setVisible(true);
            });
        }
    }
}
