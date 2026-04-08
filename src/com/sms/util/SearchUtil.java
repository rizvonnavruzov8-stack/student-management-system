package com.sms.util;

import com.sms.model.Student;
import com.sms.exception.StudentNotFoundException;

import java.util.List;

public class SearchUtil {

    /**
     * Recursive search to find a student by ID.
     */
    public static Student searchStudentById(List<Student> students, int id) throws StudentNotFoundException {
        if (students == null || students.isEmpty()) {
            throw new StudentNotFoundException("Student list is empty or null.");
        }
        return searchRecursive(students, id, 0);
    }

    private static Student searchRecursive(List<Student> students, int id, int index) throws StudentNotFoundException {
        if (index >= students.size()) {
            throw new StudentNotFoundException("Student with ID " + id + " not found.");
        }

        if (students.get(index).getId() == id) {
            return students.get(index);
        }

        return searchRecursive(students, id, index + 1);
    }
}
