package com.sms.controller;

import com.sms.exception.StudentNotFoundException;
import com.sms.model.Student;
import com.sms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller — exposes /api/students endpoints.
 * Depends on the StudentService INTERFACE (not the impl) — key to POLYMORPHISM.
 */
@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentService studentService; // Interface injection = polymorphism

    /** GET /api/students — list all */
    @GetMapping
    public List<Student> getAll() {
        return studentService.getAllStudents();
    }

    /** GET /api/students/sorted — sorted by GPA (desc) */
    @GetMapping("/sorted")
    public List<Student> getSorted() {
        return studentService.sortByGpa();
    }

    /** GET /api/students/{id} — find by numeric DB id (uses recursion) */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable int id) throws StudentNotFoundException {
        return ResponseEntity.ok(studentService.findStudentById(id));
    }

    /** GET /api/students/code/{studentId} — find by student code */
    @GetMapping("/code/{studentId}")
    public ResponseEntity<Student> getByCode(@PathVariable String studentId) throws StudentNotFoundException {
        return ResponseEntity.ok(studentService.findStudentByCode(studentId));
    }

    /** POST /api/students — add student */
    @PostMapping
    public ResponseEntity<Student> add(@RequestBody Student student) {
        return new ResponseEntity<>(studentService.addStudent(student), HttpStatus.CREATED);
    }

    /** PUT /api/students/{id} — update student */
    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable int id, @RequestBody Student student)
            throws StudentNotFoundException {
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    /** DELETE /api/students/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) throws StudentNotFoundException {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
