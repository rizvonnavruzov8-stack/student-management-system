package com.sms.service.impl;

import com.sms.exception.StudentNotFoundException;
import com.sms.model.Student;
import com.sms.service.StudentService;
import com.sms.util.FileManager;
import com.sms.util.ActivityLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * POLYMORPHISM — Implements the StudentService interface.
 * The controller depends only on StudentService (the interface),
 * not this concrete class. This is runtime polymorphism.
 *
 * RECURSION — findStudentByIdRecursive is a true recursive search
 * used in findStudentById() (not a dummy function).
 */
@Service
public class StudentServiceImpl implements StudentService {

    private final List<Student> students = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    @Autowired
    private FileManager fileManager;

    @Autowired
    private ActivityLogger activityLogger;

    @Override
    public Student addStudent(Student student) {
        student.setId(idCounter.getAndIncrement());
        students.add(student);
        fileManager.saveStudents(students);
        activityLogger.log("ADD_STUDENT", "Added: " + student.getSummary());
        return student;
    }

    @Override
    public Student updateStudent(int id, Student updated) throws StudentNotFoundException {
        // Uses recursive search here — demonstrates real usage
        Student existing = findStudentById(id);
        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setEmail(updated.getEmail());
        existing.setDateOfBirth(updated.getDateOfBirth());
        existing.setStatus(updated.getStatus());
        if (updated instanceof com.sms.model.GraduateStudent g
                && existing instanceof com.sms.model.GraduateStudent eg) {
            eg.setResearchTopic(g.getResearchTopic());
            eg.setSupervisorName(g.getSupervisorName());
        }
        existing.touch();
        fileManager.saveStudents(students);
        activityLogger.log("UPDATE_STUDENT", "Updated ID=" + id);
        return existing;
    }

    @Override
    public void deleteStudent(int id) throws StudentNotFoundException {
        Student student = findStudentById(id);
        students.remove(student);
        fileManager.saveStudents(students);
        activityLogger.log("DELETE_STUDENT", "Deleted ID=" + id);
    }

    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    /**
     * RECURSION — delegates to the recursive helper.
     * This is used in actual system operations (update, delete, grade assignment).
     */
    @Override
    public Student findStudentById(int id) throws StudentNotFoundException {
        if (students.isEmpty()) {
            throw new StudentNotFoundException(id);
        }
        return findStudentByIdRecursive(students, id, 0);
    }

    @Override
    public Student findStudentByCode(String studentId) throws StudentNotFoundException {
        return students.stream()
            .filter(s -> s.getStudentId().equalsIgnoreCase(studentId))
            .findFirst()
            .orElseThrow(() -> new StudentNotFoundException("No student with code: " + studentId));
    }

    /**
     * RECURSION — mandatory recursive search.
     * BASE CASE 1: index out of bounds → throw StudentNotFoundException.
     * BASE CASE 2: current element matches → return it.
     * RECURSIVE STEP: call self with index + 1.
     */
    @Override
    public Student findStudentByIdRecursive(List<Student> list, int id, int index)
            throws StudentNotFoundException {
        // Base case 1: reached end without finding
        if (index >= list.size()) {
            throw new StudentNotFoundException(id);
        }
        // Base case 2: found
        if (list.get(index).getId() == id) {
            return list.get(index);
        }
        // Recursive step
        return findStudentByIdRecursive(list, id, index + 1);
    }

    @Override
    public List<Student> sortByGpa() {
        List<Student> sorted = new ArrayList<>(students);
        sorted.sort(Comparator.comparingDouble(Student::getGpa).reversed());
        return sorted;
    }

    /** Called by FileManager on startup to populate in-memory list */
    public void loadStudents(List<Student> loaded) {
        students.clear();
        students.addAll(loaded);
        int maxId = loaded.stream().mapToInt(com.sms.model.BaseEntity::getId).max().orElse(0);
        idCounter.set(maxId + 1);
    }

    public List<Student> getStudentsRef() {
        return students;
    }
}
