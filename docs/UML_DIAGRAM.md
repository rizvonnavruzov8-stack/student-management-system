# Project UML Diagram

This diagram visualizes the core architecture, inheritance hierarchy, and relationships between classes.

```mermaid
classDiagram
    class BaseEntity {
        <<abstract>>
        -int id
        -LocalDateTime createdAt
        -LocalDateTime updatedAt
        +getSummary() String
    }

    class Student {
        -String studentId
        -String firstName
        -String lastName
        -String email
        -double gpa
        -List~Double~ grades
        +getSummary() String
        +recalculateGpa() void
    }

    class GraduateStudent {
        -String researchTopic
        -String supervisorName
        +getSummary() String
    }

    class Course {
        -String courseId
        -String courseName
        -int credits
    }

    class Enrollment {
        -int enrollmentId
        -int studentDbId
        -int courseDbId
        -double grade
    }

    class StudentService {
        <<interface>>
        +addStudent(Student) void
        +findStudentById(int) Student
        +sortByGpa() List~Student~
    }

    class CourseService {
        <<interface>>
        +addCourse(Course) void
        +getAllCourses() List~Course~
    }

    class EnrollmentService {
        <<interface>>
        +enrollStudent(int, int) void
        +assignGrade(int, double) void
    }

    class StudentServiceImpl {
        -List~Student~ students
        +addStudent(Student) void
        +findStudentById(int) Student
        +sortByGpa() List~Student~
        +findStudentByIdRecursive(int) Student
    }

    class CourseServiceImpl {
        -List~Course~ courses
        +addCourse(Course) void
    }

    class EnrollmentServiceImpl {
        -List~Enrollment~ enrollments
        +enrollStudent(int, int) void
    }

    class FileManager {
        +saveStudents(List~Student~ students) void
        +loadStudents() List~Student~
    }

    class ActivityLogger {
        +log(String action, String details) void
    }

    class GlobalExceptionHandler {
        +handleNotFound(Exception e) ResponseEntity~Object~
    }

    %% Relationships
    BaseEntity <|-- Student
    BaseEntity <|-- Course
    BaseEntity <|-- Enrollment
    Student <|-- GraduateStudent
    
    StudentService <|.. StudentServiceImpl
    CourseService <|.. CourseServiceImpl
    EnrollmentService <|.. EnrollmentServiceImpl
    
    StudentServiceImpl --> FileManager : uses
    StudentServiceImpl --> ActivityLogger : uses
```

## Description from Diagram:
This comprehensive diagram shows the inheritance hierarchy descending from `BaseEntity`, the implementation of multiple service interfaces, and the utility dependencies for persistence and logging.
