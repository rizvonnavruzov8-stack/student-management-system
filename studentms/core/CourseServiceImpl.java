package core;
import com.sms.exception.InvalidGradeException;
import com.sms.exception.StudentNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CourseServiceImpl implements CourseService {

    private List<Course> courseRepository;

    public CourseServiceImpl() {
        this.courseRepository = new ArrayList<>();
    }

    @Override
    public void addCourse(Course c) {
        if (c != null) {
            courseRepository.add(c);
            System.out.println("Course added successfully: " + c.getCourseName());
        }
    }

    @Override
    public void assignGrade(int studentId, String courseId, double grade) throws InvalidGradeException, StudentNotFoundException {
        // Validate grade constraint (e.g., 0-100 according to standard problem statement)
        if (grade < 0.0 || grade > 100.0) {
            throw new InvalidGradeException("Grade must be between 0 and 100. Provided: " + grade);
        }

        // Wait for Diyor and Rizvon to implement Student and StudentService
        // Real implementation would look like:
        // Student s = studentService.findStudentById(studentId);
        // if (s == null) throw new StudentNotFoundException("Student not found!");
        // We assume logic for storing student grades will align with their File Handling / Database implementations.

        System.out.println("Grade " + grade + " successfully assigned for student ID: " + studentId + " in course: " + courseId);
    }

    public List<Course> getAllCourses() {
        return courseRepository;
    }
}
