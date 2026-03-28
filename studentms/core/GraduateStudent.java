package core;

/**
 * GraduateStudent class representing a graduate student.
 * Inherits from Student, showing a deeper INHERITANCE HIERARCHY.
 */
public class GraduateStudent extends Student {
    private String thesisTitle;

    /**
     * Constructor for GraduateStudent.
     * @param id Student ID.
     * @param name Student Name.
     * @param email Student Email.
     * @param thesisTitle Research topic or thesis title.
     */
    public GraduateStudent(String id, String name, String email, String thesisTitle) {
        super(id, name, email); // Inheritance: Passing parameters up the tree.
        this.thesisTitle = thesisTitle;
    }

    public String getThesisTitle() {
        return thesisTitle;
    }

    public void setThesisTitle(String thesisTitle) {
        this.thesisTitle = thesisTitle;
    }

    /**
     * Overriding displayInfo to include thesis details.
     * More POLYMORPHISM: This version of displayInfo is more specific to Graduate Students.
     */
    @Override
    public void displayInfo() {
        super.displayInfo(); // Polymorphism: Calling the parent's version before adding more details.
        System.out.println("Thesis Title: " + thesisTitle);
    }
}
