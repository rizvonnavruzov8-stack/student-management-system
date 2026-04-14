package com.sms.util;

import com.sms.model.*;
import com.sms.service.impl.CourseServiceImpl;
import com.sms.service.impl.EnrollmentServiceImpl;
import com.sms.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FILE I/O — reads and writes all domain data to flat text files.
 * Uses BufferedWriter and BufferedReader as required.
 *
 * Format (CSV):
 *   students.txt  → type|id|studentId|firstName|lastName|email|dob|status|gpa|grades|researchTopic|supervisor
 *   courses.txt   → id|courseId|courseName|courseCode|credits|instructor
 *   enrollments.txt → enrollmentId|studentDbId|courseDbId|date|grade
 */
@Component
public class FileManager {

    @Value("${app.students-file}") private String studentsFile;
    @Value("${app.courses-file}")  private String coursesFile;
    @Value("${app.enrollments-file}") private String enrollmentsFile;

    @Lazy @Autowired private StudentServiceImpl   studentService;
    @Lazy @Autowired private CourseServiceImpl    courseService;
    @Lazy @Autowired private EnrollmentServiceImpl enrollmentService;

    // ── SAVE ──────────────────────────────────────────────────────────────────

    public void saveStudents(List<Student> students) {
        try {
            ensureDir(studentsFile);
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(studentsFile))) {
                for (Student s : students) {
                    String grades = s.getGrades().stream()
                        .map(Object::toString).reduce("", (a, b) -> a.isEmpty() ? b : a + "," + b);
                    String type = (s instanceof GraduateStudent) ? "graduate" : "student";
                    String line = type + "|" + s.getId() + "|" + s.getStudentId() + "|"
                        + s.getFirstName() + "|" + s.getLastName() + "|" + s.getEmail() + "|"
                        + s.getDateOfBirth() + "|" + s.getStatus() + "|" + s.getGpa() + "|" + grades;
                    if (s instanceof GraduateStudent g) {
                        line += "|" + g.getResearchTopic() + "|" + g.getSupervisorName();
                    } else {
                        line += "||";
                    }
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("[FileManager] Error saving students: " + e.getMessage());
        }
    }

    public void saveCourses(List<Course> courses) {
        try {
            ensureDir(coursesFile);
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(coursesFile))) {
                for (Course c : courses) {
                    bw.write(c.getId() + "|" + c.getCourseId() + "|" + c.getCourseName() + "|"
                        + c.getCourseCode() + "|" + c.getCredits() + "|" + c.getInstructorName());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("[FileManager] Error saving courses: " + e.getMessage());
        }
    }

    public void saveEnrollments(List<Enrollment> enrollments) {
        try {
            ensureDir(enrollmentsFile);
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(enrollmentsFile))) {
                for (Enrollment e : enrollments) {
                    bw.write(e.getEnrollmentId() + "|" + e.getStudentDbId() + "|"
                        + e.getCourseDbId() + "|" + e.getEnrollmentDate() + "|" + e.getGrade());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("[FileManager] Error saving enrollments: " + e.getMessage());
        }
    }

    // ── LOAD ──────────────────────────────────────────────────────────────────

    public void loadAll() throws IOException {
        loadStudents();
        loadCourses();
        loadEnrollments();
    }

    private void loadStudents() throws IOException {
        List<Student> loaded = new ArrayList<>();
        File f = new File(studentsFile);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] p = line.split("\\|", -1);
                if (p.length < 11) continue;
                String type = p[0];
                int id = Integer.parseInt(p[1]);
                Student s;
                if ("graduate".equals(type)) {
                    s = new GraduateStudent(id, p[2], p[3], p[4], p[5], p[6], p[7],
                                            p[10], p[11]);
                } else {
                    s = new Student(id, p[2], p[3], p[4], p[5], p[6], p[7]);
                }
                s.setGpa(Double.parseDouble(p[8]));
                if (!p[9].isBlank()) {
                    List<Double> grades = new ArrayList<>();
                    for (String g : p[9].split(",")) grades.add(Double.parseDouble(g));
                    s.setGrades(grades);
                }
                loaded.add(s);
            }
        }
        studentService.loadStudents(loaded);
    }

    private void loadCourses() throws IOException {
        List<Course> loaded = new ArrayList<>();
        File f = new File(coursesFile);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] p = line.split("\\|", -1);
                if (p.length < 6) continue;
                loaded.add(new Course(Integer.parseInt(p[0]), p[1], p[2], p[3],
                                      Integer.parseInt(p[4]), p[5]));
            }
        }
        courseService.loadCourses(loaded);
    }

    private void loadEnrollments() throws IOException {
        List<Enrollment> loaded = new ArrayList<>();
        File f = new File(enrollmentsFile);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] p = line.split("\\|", -1);
                if (p.length < 5) continue;
                Enrollment e = new Enrollment(Integer.parseInt(p[0]),
                                              Integer.parseInt(p[1]),
                                              Integer.parseInt(p[2]));
                e.setEnrollmentDate(p[3]);
                e.setGrade(Double.parseDouble(p[4]));
                loaded.add(e);
            }
        }
        enrollmentService.loadEnrollments(loaded);
    }

    private void ensureDir(String filePath) throws IOException {
        Path parent = Paths.get(filePath).getParent();
        if (parent != null) Files.createDirectories(parent);
    }
}
