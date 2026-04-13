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
 */
@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> getAll() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable int id) throws CourseNotFoundException {
        return ResponseEntity.ok(courseService.findCourseById(id));
    }

    @PostMapping
    public ResponseEntity<Course> add(@RequestBody Course course) {
        return new ResponseEntity<>(courseService.addCourse(course), HttpStatus.CREATED);
    }
}
