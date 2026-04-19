package com.sms.service;

import com.sms.exception.StudentNotFoundException;
import com.sms.model.Student;

import java.util.List;

/**
 * INTERFACE — defines the contract for all student operations.
 * Demonstrates POLYMORPHISM: the caller only depends on this interface,
 * not on the concrete StudentServiceImpl.
 */
public interface StudentService {

    Student addStudent(Student student);

    Student updateStudent(int id, Student student) throws StudentNotFoundException;

    void deleteStudent(int id) throws StudentNotFoundException;

    List<Student> getAllStudents();

    /** Recursive search by numeric database ID */
    Student findStudentById(int id) throws StudentNotFoundException;

    /** Search by student-code string (e.g. "STU-001") */
    Student findStudentByCode(String studentId) throws StudentNotFoundException;

    /**
     * RECURSION — mandatory recursive search.
     * Must be used in real system logic (not a dummy method).
     */
    Student findStudentByIdRecursive(List<Student> list, int id, int index) throws StudentNotFoundException;

    List<Student> sortByGpa();

    /** Called by FileManager on startup to restore persisted data */
    void loadStudents(List<Student> loaded);

    /** Returns the live list reference — used internally by EnrollmentServiceImpl */
    List<Student> getStudentsRef();
}
