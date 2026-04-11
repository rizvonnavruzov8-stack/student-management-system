package com.sms.model;

/**
 * 🎓 EXPLANATION: GraduateStudent (Specialized Type)
 * 
 * 1. REPRESENTATION:
 *    A student who is pursuing a higher degree, requiring a thesis topic.
 * 
 * 2. WHY IT EXISTS:
 *    To demonstrate that some students have additional attributes and behaviors.
 * 
 * 3. OOP CONCEPTS USED:
 *    - INHERITANCE: Extends 'Student', gaining all its properties and methods.
 *    - POLYMORPHISM (Overriding): It overrides 'calculateGPA' to provide specialized behavior.
 */
public class GraduateStudent extends Student {
    
    private String thesisTopic;

    public GraduateStudent() {
        super();
    }

    public GraduateStudent(int id, String name, double gpa, String thesisTopic) {
        super(id, name, gpa);
        this.thesisTopic = thesisTopic;
    }

    public String getThesisTopic() {
        return thesisTopic;
    }

    public void setThesisTopic(String thesisTopic) {
        this.thesisTopic = thesisTopic;
    }

    /**
     * 🎓 POLYMORPHISM IN ACTION:
     * Overriding method from Student class.
     * Graduate students might have a different way to calculate or scale their GPA.
     */
    @Override
    public double calculateGPA() {
        // For demonstration, we just return the GPA, 
        // but in reality, graduate GPA thresholds are often higher.
        return super.getGpa();
    }

    @Override
    public String toString() {
        return "GraduateStudent{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", gpa=" + getGpa() +
                ", thesisTopic='" + thesisTopic + '\'' +
                ", courses=" + getCourses() +
                '}';
    }
}
