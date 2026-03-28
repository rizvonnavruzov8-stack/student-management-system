package core;

/**
 * Student class representing a student in the system.
 * Inherits from BaseEntity, demonstrating the OOP concept of INHERITANCE.
 */
public class Student extends BaseEntity {
    private double gpa;
    private String email;

    /**
     * Constructor for Student.
     * Calls the parent constructor using 'super'.
     * @param id Student ID.
     * @param name Student Name.
     * @param email Student Email.
     */
    public Student(String id, String name, String email) {
        super(id, name); // Inheritance: Reusing the base class constructor.
        this.email = email;
    }

    // Encapsulation: GPA is managed within the class.
    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        if (gpa >= 0.0 && gpa <= 4.0) {
            this.gpa = gpa;
        } else {
            System.out.println("Invalid GPA value. Must be between 0.0 and 4.0.");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Implementation of the abstract method from BaseEntity.
     * Demonstrates POLYMORPHISM by providing a specific implementation.
     */
    @Override
    public void displayInfo() {
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("GPA: " + getGpa());
    }
}
