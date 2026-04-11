package com.sms.util;

import com.sms.model.Course;
import com.sms.model.Student;
import com.sms.model.GraduateStudent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 🎓 EXPLANATION: FileManager (Persistence Layer)
 * 
 * 1. WHY IT EXISTS:
 *    Computers lose data when a program ends. We use files (.txt) to store data permanently.
 * 
 * 2. HOW IT WORKS (Writing):
 *    - Use 'BufferedWriter' to write text to a file efficiently.
 *    - Data is formatted as Comma-Separated Values (CSV), e.g., "1,John Doe,3.5".
 * 
 * 3. HOW IT WORKS (Reading):
 *    - Use 'BufferedReader' to read files line-by-line.
 *    - Use 'String.split(",")' to break the CSV line back into individual data pieces.
 * 
 * 4. EXCEPTION HANDLING:
 *    File I/O is risky (file might be missing or protected). We MUST use try-catch to 
 *    handle 'IOException'.
 */
public class FileManager {

    private static final String STUDENT_FILE = "students.txt";
    private static final String COURSE_FILE = "courses.txt";

    /**
     * Saves students to students.txt
     */
    public static void saveStudents(List<Student> students) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(STUDENT_FILE))) {
            for (Student s : students) {
                // Format: id,name,gpa,isGraduate,thesisTopic,[grade1|grade2|...]
                StringBuilder sb = new StringBuilder();
                sb.append(s.getId()).append(",")
                  .append(s.getName()).append(",")
                  .append(s.getGpa()).append(",");
                
                if (s instanceof GraduateStudent) {
                    sb.append("true,").append(((GraduateStudent) s).getThesisTopic());
                } else {
                    sb.append("false,N/A");
                }

                // Add grades list at the end
                sb.append(",");
                String gradesStr = s.getGrades().stream()
                                    .map(Object::toString)
                                    .collect(Collectors.joining("|"));
                sb.append(gradesStr);

                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            Logger.log("Error saving students: " + e.getMessage());
        }
    }

    /**
     * Loads students from students.txt
     */
    public static List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();
        File file = new File(STUDENT_FILE);
        if (!file.exists()) return students;

        try (BufferedReader br = new BufferedReader(new FileReader(STUDENT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    double gpa = Double.parseDouble(parts[2]);
                    boolean isGraduate = Boolean.parseBoolean(parts[3]);
                    String thesis = parts[4];
                    String gradesPart = parts[5];

                    Student s;
                    if (isGraduate) {
                        s = new GraduateStudent(id, name, gpa, thesis);
                    } else {
                        s = new Student(id, name, gpa);
                    }

                    // Load grades
                    if (!gradesPart.isEmpty()) {
                        String[] grades = gradesPart.split("\\|");
                        for (String g : grades) {
                            s.getGrades().add(Double.parseDouble(g));
                        }
                    }
                    students.add(s);
                }
            }
        } catch (IOException | NumberFormatException e) {
            Logger.log("Error loading students: " + e.getMessage());
        }
        return students;
    }

    public static void saveCourses(List<Course> courses) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(COURSE_FILE))) {
            for (Course c : courses) {
                bw.write(c.getCourseId() + "," + c.getCourseName() + "," + c.getCredits());
                bw.newLine();
            }
        } catch (IOException e) {
            Logger.log("Error saving courses: " + e.getMessage());
        }
    }

    public static List<Course> loadCourses() {
        List<Course> courses = new ArrayList<>();
        File file = new File(COURSE_FILE);
        if (!file.exists()) return courses;

        try (BufferedReader br = new BufferedReader(new FileReader(COURSE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    courses.add(new Course(Integer.parseInt(parts[0]), parts[1], Integer.parseInt(parts[2])));
                }
            }
        } catch (IOException | NumberFormatException e) {
            Logger.log("Error loading courses: " + e.getMessage());
        }
        return courses;
    }
}
