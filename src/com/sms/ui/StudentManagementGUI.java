package com.sms.ui;

import com.sms.exception.StudentNotFoundException;
import com.sms.model.Course;
import com.sms.model.GraduateStudent;
import com.sms.model.Student;
import com.sms.service.impl.CourseServiceImpl;
import com.sms.service.impl.StudentServiceImpl;
import com.sms.util.AuthUtil;
import com.sms.util.FileManager;
import com.sms.util.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * 🎓 EXPLANATION: StudentManagementGUI (Graphical User Interface)
 * 
 * 1. WHY IT EXISTS:
 *    To provide a modern, easy-to-use visual interface for the Student Management System.
 * 
 * 2. STRUCTURE:
 *    - Uses CardLayout to switch between Login and Dashboard.
 *    - Dashboard uses JTabbedPane to organize Student and Course management.
 *    - Integrates with existing services for data persistence and logic.
 */
public class StudentManagementGUI extends JFrame {

    private final StudentServiceImpl studentService;
    private final CourseServiceImpl courseService;
    
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    // Login Components
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    // Dashboard Components
    private DefaultTableModel studentTableModel;
    private JTable studentTable;
    private JTextField searchField; // New search field
    private DefaultTableModel courseTableModel;
    private JTable courseTable;

    public StudentManagementGUI() {
        this.studentService = new StudentServiceImpl();
        this.courseService = new CourseServiceImpl();
        
        // Load data
        FileManager.loadStudents().forEach(studentService::addStudent);
        // Course service doesn't have an explicit load in Menu.java but FileManager has save
        // We'll assume courses are managed in-memory or loaded as needed
        
        setupUI();
    }

    private void setupUI() {
        setTitle("🎓 Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createLoginPanel(), "LOGIN");
        mainPanel.add(createDashboardPanel(), "DASHBOARD");

        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 247, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("🎓 SMS Login", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; usernameField = new JTextField(15); panel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; passwordField = new JPasswordField(15); panel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(52, 152, 219));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.addActionListener(e -> handleLogin());
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        return panel;
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        AuthUtil.login(username, password);
        JOptionPane.showMessageDialog(this, "Logged in as: " + AuthUtil.getCurrentRole());
        
        refreshStudentTable();
        refreshCourseTable();
        cardLayout.show(mainPanel, "DASHBOARD");
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("SansSerif", Font.PLAIN, 14));

        tabbedPane.addTab("👤 Students", createStudentTab());
        tabbedPane.addTab("📚 Courses", createCourseTab());
        tabbedPane.addTab("📊 Reports", createReportTab());

        panel.add(tabbedPane, BorderLayout.CENTER);

        // Footer with Logout and Save
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            AuthUtil.logout();
            cardLayout.show(mainPanel, "LOGIN");
        });
        
        JButton exitButton = new JButton("Save & Exit");
        exitButton.addActionListener(e -> {
            FileManager.saveStudents(studentService.getAllStudents());
            FileManager.saveCourses(courseService.getAllCourses());
            System.exit(0);
        });

        footer.add(logoutButton);
        footer.add(exitButton);
        panel.add(footer, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createStudentTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);

        // Top Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("🔍 Search:"));
        searchField = new JTextField(20);
        searchField.setToolTipText("Search by ID, Name, or Surname");
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                refreshStudentTable(searchField.getText());
            }
        });
        searchPanel.add(searchField);
        panel.add(searchPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Name", "Surname", "Email", "Gender", "GPA", "Type"};
        studentTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        studentTable = new JTable(studentTableModel);
        studentTable.setRowHeight(25);
        studentTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        studentTable.getTableHeader().setBackground(new Color(52, 73, 94));
        studentTable.getTableHeader().setForeground(Color.WHITE);
        studentTable.setSelectionBackground(new Color(174, 214, 241));
        
        panel.add(new JScrollPane(studentTable), BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.setBackground(Color.WHITE);
        JButton addBtn = createStyledButton("Add Student", new Color(46, 204, 113));
        JButton updateBtn = createStyledButton("Update Name", new Color(52, 152, 219));
        JButton deleteBtn = createStyledButton("Delete", new Color(231, 76, 60));
        JButton gradeBtn = createStyledButton("Add Grade", new Color(155, 89, 182));

        addBtn.addActionListener(e -> showAddStudentDialog());
        updateBtn.addActionListener(e -> handleUpdateStudent());
        deleteBtn.addActionListener(e -> handleDeleteStudent());
        gradeBtn.addActionListener(e -> handleAddGrade());

        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(gradeBtn);

        panel.add(btnPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return btn;
    }

    private void showAddStudentDialog() {
        if (!AuthUtil.isAdmin()) {
            JOptionPane.showMessageDialog(this, "Access Denied: Admin only.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField idF = new JTextField();
        JTextField nameF = new JTextField();
        JTextField surnameF = new JTextField();
        JTextField emailF = new JTextField();
        JComboBox<String> genderF = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        JTextField gpaF = new JTextField("0.0");
        JCheckBox isGrad = new JCheckBox("Is Graduate Student?");
        JTextField thesisF = new JTextField();
        thesisF.setEnabled(false);
        
        isGrad.addActionListener(ae -> thesisF.setEnabled(isGrad.isSelected()));

        Object[] message = {
            "ID:", idF,
            "Name:", nameF,
            "Surname:", surnameF,
            "Email:", emailF,
            "Gender:", genderF,
            "GPA (0.0 - 4.0):", gpaF,
            "", isGrad,
            "Thesis Topic (if Graduate):", thesisF
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Student", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idF.getText());
                String name = nameF.getText();
                String surname = surnameF.getText();
                String email = emailF.getText();
                String gender = (String) genderF.getSelectedItem();
                double gpa = Double.parseDouble(gpaF.getText());
                
                if (gpa < 0 || gpa > 4) {
                    JOptionPane.showMessageDialog(this, "GPA must be between 0 and 4.");
                    return;
                }

                Student s;
                if (isGrad.isSelected()) {
                    s = new GraduateStudent(id, name, surname, email, gender, gpa, thesisF.getText());
                } else {
                    s = new Student(id, name, surname, email, gender, gpa);
                }
                
                studentService.addStudent(s);
                Logger.log("GUI: Added student " + name + " " + surname);
                refreshStudentTable();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input format!");
            }
        }
    }

    private void handleUpdateStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student first.");
            return;
        }

        int id = (int) studentTableModel.getValueAt(selectedRow, 0);
        String currentName = (String) studentTableModel.getValueAt(selectedRow, 1);
        
        String newName = JOptionPane.showInputDialog(this, "New name for ID " + id + ":", currentName);
        if (newName != null && !newName.trim().isEmpty()) {
            try {
                Student s = studentService.findStudentById(id);
                s.setName(newName);
                studentService.updateStudent(s);
                refreshStudentTable();
            } catch (StudentNotFoundException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }

    private void handleDeleteStudent() {
        if (!AuthUtil.isAdmin()) {
            JOptionPane.showMessageDialog(this, "Admin rights required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a student to delete.");
            return;
        }

        int id = (int) studentTableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete ID: " + id + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            studentService.deleteStudent(id);
            refreshStudentTable();
        }
    }

    private void handleAddGrade() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a student to add grade.");
            return;
        }

        int id = (int) studentTableModel.getValueAt(selectedRow, 0);
        String gradeStr = JOptionPane.showInputDialog(this, "Enter Grade (0-100):");
        
        if (gradeStr != null) {
            try {
                double grade = Double.parseDouble(gradeStr);
                Student s = studentService.findStudentById(id);
                s.addGrade(grade);
                refreshStudentTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    private void refreshStudentTable() {
        refreshStudentTable(null);
    }

    private void refreshStudentTable(String query) {
        studentTableModel.setRowCount(0);
        List<Student> students = (query == null || query.trim().isEmpty()) 
                                ? studentService.getAllStudents() 
                                : studentService.searchStudents(query);
                                
        for (Student s : students) {
            studentTableModel.addRow(new Object[]{
                s.getId(),
                s.getName(),
                s.getSurname(),
                s.getEmail(),
                s.getGender(),
                String.format("%.2f", s.getGpa()),
                s instanceof GraduateStudent ? "Graduate" : "Undergrad"
            });
        }
    }

    private JPanel createCourseTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = {"ID", "Name", "Credits"};
        courseTableModel = new DefaultTableModel(columns, 0);
        courseTable = new JTable(courseTableModel);
        panel.add(new JScrollPane(courseTable), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addCourseBtn = new JButton("Add Course");
        JButton enrollBtn = new JButton("Enroll Selected Student");

        addCourseBtn.addActionListener(e -> showAddCourseDialog());
        enrollBtn.addActionListener(e -> handleEnrollment());

        btnPanel.add(addCourseBtn);
        btnPanel.add(enrollBtn);
        panel.add(btnPanel, BorderLayout.NORTH);

        return panel;
    }

    private void showAddCourseDialog() {
        JTextField idF = new JTextField();
        JTextField nameF = new JTextField();
        JTextField creditF = new JTextField();

        Object[] message = {
            "Course ID:", idF,
            "Course Name:", nameF,
            "Credits:", creditF
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add New Course", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idF.getText());
                String name = nameF.getText();
                int credits = Integer.parseInt(creditF.getText());
                courseService.addCourse(new Course(id, name, credits));
                refreshCourseTable();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid number format!");
            }
        }
    }

    private void handleEnrollment() {
        int sRow = studentTable.getSelectedRow();
        int cRow = courseTable.getSelectedRow();

        if (sRow == -1 || cRow == -1) {
            JOptionPane.showMessageDialog(this, "Select both a student and a course.");
            return;
        }

        int sId = (int) studentTableModel.getValueAt(sRow, 0);
        int cId = (int) courseTableModel.getValueAt(cRow, 0);

        try {
            Student s = studentService.findStudentById(sId);
            Course target = null;
            for (Course c : courseService.getAllCourses()) {
                if (c.getCourseId() == cId) {
                    target = c;
                    break;
                }
            }
            if (target != null) {
                courseService.assignCourse(s, target);
                JOptionPane.showMessageDialog(this, "Successfully enrolled " + s.getName() + " in " + target.getCourseName());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void refreshCourseTable() {
        courseTableModel.setRowCount(0);
        for (Course c : courseService.getAllCourses()) {
            courseTableModel.addRow(new Object[]{c.getCourseId(), c.getCourseName(), c.getCredits()});
        }
    }

    private JPanel createReportTab() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.BOTH;

        JButton summaryBtn = new JButton("System-wide Summary");
        JButton topBtn = new JButton("Top 5 Students");
        JButton logsBtn = new JButton("View Logs Location");

        summaryBtn.addActionListener(e -> {
            // We'll capture the output or just show a message
            // ReportUtil prints to console, but we can summarize here
            List<Student> students = studentService.getAllStudents();
            double avg = students.stream().mapToDouble(Student::getGpa).average().orElse(0.0);
            JOptionPane.showMessageDialog(this, "Total Students: " + students.size() + "\nAverage GPA: " + String.format("%.2f", avg));
        });

        topBtn.addActionListener(e -> {
            List<Student> students = studentService.getAllStudents();
            students.sort((s1, s2) -> Double.compare(s2.getGpa(), s1.getGpa()));
            StringBuilder sb = new StringBuilder("--- TOP STUDENTS ---\n");
            for (int i = 0; i < Math.min(5, students.size()); i++) {
                Student s = students.get(i);
                sb.append(i + 1).append(". ").append(s.getName()).append(" (GPA: ").append(String.format("%.2f", s.getGpa())).append(")\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        logsBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logs are stored in: /home/student/Documents/Projects/student-management-system/logs.txt");
        });

        gbc.gridx = 0; gbc.gridy = 0; panel.add(summaryBtn, gbc);
        gbc.gridx = 1; panel.add(topBtn, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; panel.add(logsBtn, gbc);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentManagementGUI().setVisible(true);
        });
    }
}
