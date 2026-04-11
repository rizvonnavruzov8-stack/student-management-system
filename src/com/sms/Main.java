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
        Logger.log("System starting up...");
        System.out.println("Initializing Student Management System...");
        
        Menu menu = new Menu();
        menu.start();
    }
}
