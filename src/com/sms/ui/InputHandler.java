package com.sms.ui;

import com.sms.exception.InvalidGradeException;
import java.util.Scanner;

/**
 * 🎓 EXPLANATION: InputHandler (UI Helper)
 * 
 * 1. WHY IT EXISTS:
 *    To prevent the program from crashing if the user types something wrong (e.g., text instead of number).
 * 
 * 2. EXCEPTION HANDLING:
 *    We use try-catch inside the methods to handle 'InputMismatchException' and 'NumberFormatException'.
 */
public class InputHandler {

    private static final Scanner scanner = new Scanner(System.in);

    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a whole number.");
            }
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double val = Double.parseDouble(scanner.nextLine());
                if (val < 0 || val > 100) {
                    throw new InvalidGradeException("Grade must be between 0 and 100.");
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a decimal number.");
            } catch (InvalidGradeException e) {
                System.out.println("❌ " + e.getMessage());
            }
        }
    }

    public static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
