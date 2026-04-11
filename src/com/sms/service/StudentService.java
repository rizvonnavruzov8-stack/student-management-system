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
}
