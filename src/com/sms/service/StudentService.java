package com.sms.service;

import com.sms.model.Student;

public interface StudentService {
    void addStudent(Student student);
    void deleteStudent(int id);
    void updateStudent(Student student);
    Student findStudentById(int id);
}
