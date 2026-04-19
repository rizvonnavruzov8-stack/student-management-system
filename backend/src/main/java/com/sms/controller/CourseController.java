package com.sms.controller;

import com.sms.exception.CourseNotFoundException;
import com.sms.model.Course;
import com.sms.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller — exposes /api/courses endpoints.
 * Depends on the CourseService INTERFACE (not the impl) — runtime polymorphism.
 */
@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {

    @Autowired
    private CourseService courseService; // Interface injection = polymorphism

    /** GET /api/courses — list all courses */
    @GetMapping
    public List<Course> getAll() {
        return courseService.getAllCourses();
    }

    /** GET /api/courses/{id} — find by numeric DB id */
    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable int id) throws CourseNotFoundException {
        return ResponseEntity.ok(courseService.findCourseById(id));
    }

    /** POST /api/courses — add new course */
    @PostMapping
    public ResponseEntity<Course> add(@RequestBody Course course) {
        return new ResponseEntity<>(courseService.addCourse(course), HttpStatus.CREATED);
    }

    /** PUT /api/courses/{id} — update existing course */
    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable int id, @RequestBody Course course)
            throws CourseNotFoundException {
        return ResponseEntity.ok(courseService.updateCourse(id, course));
    }

    /** DELETE /api/courses/{id} — remove course */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) throws CourseNotFoundException {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
