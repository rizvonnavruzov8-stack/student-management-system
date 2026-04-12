package core;
import java.util.List;

public class SearchUtil {

    /**
     * Recursively searches for a student by their ID.
     * @param students The list of students
     * @param targetId The ID to search for
     * @param index The current index to check (initially 0)
     * @return The Student if found, otherwise null
     */
    public static Student recursiveSearch(List<Student> students, String targetId, int index) {
        // Base case: out of bounds or empty list
        if (students == null || index < 0 || index >= students.size()) {
            return null;
        }

        Student current = students.get(index);

        // Base case: Match found
        if (current != null && current.getId().equals(targetId)) {
            return current;
        }

        // Recursive step: Search in the rest of the list
        return recursiveSearch(students, targetId, index + 1);
    }
}
