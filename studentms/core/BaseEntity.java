package core;

/**
 * Abstract base class for all entities in the Student Management System.
 * Demonstrates the OOP concept of ABSTRACTION by providing a common structure
 * for entities that cannot be instantiated on their own.
 */
public abstract class BaseEntity {
    private String id;
    private String name;

    /**
     * Constructor for BaseEntity.
     * Demonstrates encapsulation by using private fields and providing a constructor.
     * @param id The unique identifier for the entity.
     * @param name The name of the entity.
     */
    public BaseEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Encapsulation: Using getters and setters to control access to private fields.
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Abstract method to display details.
     * Demonstrates abstraction: subclasses must provide their own implementation.
     */
    public abstract void displayInfo();
}
