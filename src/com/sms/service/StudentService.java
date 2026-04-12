package com.sms.service;

import com.sms.exception.StudentNotFoundException;
import com.sms.model.Student;
import java.util.List;

public interface StudentService {
    void addStudent(Student student);
    void deleteStudent(int id);
    void updateStudent(Student student);
    Student findStudentById(int id) throws StudentNotFoundException;
    List<Student> getAllStudents();

    /**
     * Searches for students by a query string.
     * @param query The search query
     * @return List of matching students
     */
    List<Student> searchStudents(String query);
}
