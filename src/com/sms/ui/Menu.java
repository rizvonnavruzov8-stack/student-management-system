package com.sms.ui;

import com.sms.model.Course;
import com.sms.model.Student;
import com.sms.model.GraduateStudent;
import com.sms.service.impl.StudentServiceImpl;
import com.sms.service.impl.CourseServiceImpl;
import com.sms.util.FileManager;
import com.sms.util.Logger;
import com.sms.util.AuthUtil;
import com.sms.util.ReportUtil;
import com.sms.util.SortUtil;
import com.sms.exception.StudentNotFoundException;

import java.util.List;

/**
 * 🎓 EXPLANATION: Menu (UI Layer)
 * 
 * 1. WHY IT EXISTS:
 *    To provide a visual interface for the user to interact with the system.
 *    Now supports Role-Based Access as per Section 4 of the proposal.
 */
public class Menu {

    private final StudentServiceImpl studentService;
    private final CourseServiceImpl courseService;
    private boolean isRunning = true;

    public Menu() {
        this.studentService = new StudentServiceImpl();
        this.courseService = new CourseServiceImpl();
        
        // Load data at startup
        FileManager.loadStudents().forEach(studentService::addStudent);
    }

    public void start() {
        System.out.println("--- 🎓 WELCOME TO STUDENT MANAGEMENT SYSTEM ---");
        login();
        while (isRunning) {
            displayMainMenu();
        }
    }

    private void login() {
        System.out.println("\n--- LOGIN ---");
        String user = InputHandler.readString("Username: ");
        String pass = InputHandler.readString("Password: ");
        AuthUtil.login(user, pass);
        System.out.println("Login successful as: " + AuthUtil.getCurrentRole());
    }

    private void displayMainMenu() {
        System.out.println("\n--- 🏠 MAIN MENU (" + AuthUtil.getCurrentRole() + ") ---");
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Grade & Performance");
        System.out.println("4. Reports & Logs");
        System.out.println("5. Logout/Switch User");
        System.out.println("6. Save and Exit");

        int choice = InputHandler.readInt("Enter choice: ");
        switch (choice) {
            case 1 -> studentMenu();
            case 2 -> courseMenu();
            case 3 -> gradeMenu();
            case 4 -> reportMenu();
            case 5 -> login();
            case 6 -> exit();
            default -> System.out.println("Invalid choice!");
        }
    }

    private void studentMenu() {
        System.out.println("\n--- 👤 STUDENT MANAGEMENT ---");
        System.out.println("1. Add Student (Root/Admin)");
        System.out.println("2. Search Student (Recursive)");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Display All Students");
        System.out.println("0. Back");

        int choice = InputHandler.readInt("Enter choice: ");
        if (choice == 1 && !AuthUtil.isAdmin()) {
            System.out.println("❌ Access Denied: Admin only.");
            return;
        }

        switch (choice) {
            case 1 -> addStudent();
            case 2 -> searchStudent();
            case 3 -> updateStudent();
            case 4 -> deleteStudent();
            case 5 -> displayStudents();
        }
    }

    private void courseMenu() {
        System.out.println("\n--- 📚 COURSE MANAGEMENT ---");
        System.out.println("1. Add New Course");
        System.out.println("2. List All Courses");
        System.out.println("3. Enroll Student in Course");
        System.out.println("0. Back");

        int choice = InputHandler.readInt("Enter choice: ");
        switch (choice) {
            case 1 -> addCourse();
            case 2 -> listCourses();
            case 3 -> enrollStudent();
        }
    }

    private void gradeMenu() {
        System.out.println("\n--- 📈 GRADE & PERFORMANCE ---");
        System.out.println("1. Assign/Add Grade (0-100)");
        System.out.println("2. Calculate System-wide GPA");
        System.out.println("0. Back");

        int choice = InputHandler.readInt("Enter choice: ");
        switch (choice) {
            case 1 -> assignGrade();
            case 2 -> ReportUtil.generateSystemSummary(studentService.getAllStudents());
        }
    }

    private void reportMenu() {
        System.out.println("\n--- 📊 REPORTS & LOGS ---");
        System.out.println("1. Top Students Report");
        System.out.println("2. View System Logs");
        System.out.println("0. Back");

        int choice = InputHandler.readInt("Enter choice: ");
        switch (choice) {
            case 1 -> ReportUtil.generateTopStudentReport(studentService.getAllStudents(), 5);
            case 2 -> viewLogs();
        }
    }

    // --- Sub-functionalities ---

    private void addStudent() {
        int id = InputHandler.readInt("Enter ID: ");
        String name = InputHandler.readString("Enter Name: ");
        String surname = InputHandler.readString("Enter Surname: ");
        String email = InputHandler.readString("Enter Email: ");
        String gender = InputHandler.readString("Enter Gender: ");
        double gpa = InputHandler.readDouble("Enter GPA (0.0 - 4.0): ");
        boolean isGrad = InputHandler.readString("Is Graduate? (y/n): ").equalsIgnoreCase("y");
        
        Student s;
        if (isGrad) {
            String thesis = InputHandler.readString("Enter Thesis Topic: ");
            s = new GraduateStudent(id, name, surname, email, gender, gpa, thesis);
        } else {
            s = new Student(id, name, surname, email, gender, gpa);
        }
        studentService.addStudent(s);
        Logger.log("Added student: " + name + " " + surname);
    }

    private void addCourse() {
        int id = InputHandler.readInt("Course ID: ");
        String name = InputHandler.readString("Course Name: ");
        int credits = InputHandler.readInt("Credits: ");
        courseService.addCourse(new Course(id, name, credits));
        Logger.log("Created course: " + name);
    }

    private void enrollStudent() {
        try {
            int sId = InputHandler.readInt("Student ID: ");
            Student s = studentService.findStudentById(sId);
            listCourses();
            int cId = InputHandler.readInt("Course ID to Enroll: ");
            for (Course c : courseService.getAllCourses()) {
                if (c.getCourseId() == cId) {
                    courseService.assignCourse(s, c);
                    System.out.println("✅ Student enrolled successfully!");
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
        }
    }

    private void assignGrade() {
        try {
            int sId = InputHandler.readInt("Student ID: ");
            Student s = studentService.findStudentById(sId);
            double grade = InputHandler.readDouble("Enter Grade (0-100): ");
            s.addGrade(grade);
            System.out.println("✅ Grade added. New GPA: " + String.format("%.2f", s.getGpa()));
        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
        }
    }

    private void updateStudent() {
        try {
            int id = InputHandler.readInt("Enter ID to Update: ");
            Student s = studentService.findStudentById(id);
            s.setName(InputHandler.readString("Change Name to: "));
            System.out.println("✅ Updated successfully!");
        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
        }
    }

    private void deleteStudent() {
        if (!AuthUtil.isAdmin()) {
            System.out.println("❌ Admin rights required.");
            return;
        }
        int id = InputHandler.readInt("Enter ID to Delete: ");
        studentService.deleteStudent(id);
        System.out.println("✅ Deleted (if existed).");
    }

    private void listCourses() {
        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) System.out.println("No courses registered.");
        else courses.forEach(System.out::println);
    }

    private void displayStudents() {
        studentService.getAllStudents().forEach(System.out::println);
    }

    private void searchStudent() {
        try {
            int id = InputHandler.readInt("Search ID: ");
            System.out.println(studentService.findStudentById(id));
        } catch (StudentNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewLogs() {
        System.out.println("📜 Checking logs.txt currently stored in root...");
        // In a real app we'd read logs.txt here, but for now we'll just confirm path
        System.out.println("Location: /home/student/Documents/Projects/student-management-system/logs.txt");
    }

    private void exit() {
        FileManager.saveStudents(studentService.getAllStudents());
        FileManager.saveCourses(courseService.getAllCourses());
        System.out.println("🚀 Persistence finalized. Session ended.");
        isRunning = false;
    }
}
