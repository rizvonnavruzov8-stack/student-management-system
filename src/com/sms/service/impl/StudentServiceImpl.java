package com.sms.service.impl;

import com.sms.model.Student;
import com.sms.service.StudentService;
import com.sms.util.SearchUtil;
import com.sms.exception.StudentNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * 🎓 EXPLANATION: StudentServiceImpl (Business Logic Implementation)
 * 
 * 1. WHY IT EXISTS:
 *    To manage the collection of students and perform CRUD (Create, Read, Update, Delete) operations.
 * 
 * 2. CRITICAL METHODS:
 *    - addStudent: Adds a new student to the system.
 *    - findStudentById: Uses SearchUtil (Recursion) to locate a student.
 *    - deleteStudent: Removes a student by their ID.
 * 
 * 3. EXCEPTION HANDLING:
 *    Methods like 'findStudentById' now 'throw StudentNotFoundException', forcing the UI 
 *    to handle the case where a student doesn't exist.
 */
public class StudentServiceImpl implements StudentService {

    private List<Student> students;

    public StudentServiceImpl() {
        this.students = new ArrayList<>();
    }

    @Override
    public void addStudent(Student student) {
        if (student != null) {
            this.students.add(student);
        }
    }

    @Override
    public void deleteStudent(int id) {
        try {
            Student student = findStudentById(id);
            this.students.remove(student);
        } catch (StudentNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void updateStudent(Student student) {
        if (student == null) return;
        
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == student.getId()) {
                students.set(i, student);
                return;
            }
        }
    }

    @Override
    public Student findStudentById(int id) throws StudentNotFoundException {
        // Logic Delegation: Using the recursive utility to find the student
        return SearchUtil.searchStudentById(this.students, id);
    }

    /**
     * Helper method to get the full list of students.
     * @return List of all students
     */
    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<>(this.students);
    }
}
