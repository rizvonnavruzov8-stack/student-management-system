package core;

/**
 * Main demonstration class for the Student Management System.
 * This class shows how the implemented entities work together.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("--- Student Management System: OOP Demonstration ---\n");

        // Polmorphism: Using the superclass type (Student) to hold a subclass object (GraduateStudent).
        Student student1 = new Student("S101", "Diyor Davlatkhojaev", "diyor@example.com");
        student1.setGpa(3.8);

        GraduateStudent GradStudent1 = new GraduateStudent("G202", "Aslan Rahmatulloev", "aslan@example.com", "Advanced Algorithms in Java");
        GradStudent1.setGpa(3.9);

        // Demonstrating the displayInfo polymorphism
        System.out.println("Processing Student 1:");
        student1.displayInfo();
        System.out.println();

        System.out.println("Processing Graduate Student 1:");
        GradStudent1.displayInfo();
        System.out.println();

        // Encapsulation Check
        System.out.println("Trying to set an invalid GPA for Rizvon (4.5):");
        Student rizvon = new Student("L001", "Rizvon Navruzov", "rizvon@example.com");
        rizvon.setGpa(4.5); // This should trigger the validation logic.
        rizvon.setGpa(4.0);
        
        System.out.println("\nFinal Result for Rizvon:");
        rizvon.displayInfo();
    }
}
