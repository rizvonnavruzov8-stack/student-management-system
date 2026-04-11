package com.sms.model;

/**
 * 🎓 EXPLANATION: BaseEntity (The Root Class)
 * 
 * 1. REPRESENTATION:
 *    This class represents a generic entity in our system that has a unique identifier (ID).
 * 
 * 2. WHY IT EXISTS:
 *    Instead of adding an 'id' field to every class (Student, Course, etc.), we put it here.
 *    This promotes DRY (Don't Repeat Yourself) principle.
 * 
 * 3. OOP CONCEPTS USED:
 *    - ABSTRACTION: This class is 'abstract', meaning you cannot create a "BaseEntity" object.
 *      It only exists to be inherited by other classes.
 *    - ENCAPSULATION: The 'id' field is 'private'. It can only be accessed or modified 
 *      through public 'getter' and 'setter' methods.
 */
public abstract class BaseEntity {
    
    // Encapsulation: Private field, not directly accessible from outside
    private int id;

    // Default Constructor
    public BaseEntity() {
    }

    // Parameterized Constructor
    public BaseEntity(int id) {
        this.id = id;
    }

    /**
     * Getter method to safely retrieve the private ID.
     * @return the unique identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Setter method to safely update the private ID.
     * @param id the new identifier
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }
}
