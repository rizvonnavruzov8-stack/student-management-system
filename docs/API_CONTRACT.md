# API Contract: Student Management System (Core)

This document outlines the OOP structure, method signatures, and data formats for the models and interfaces.

## 1. Class Definitions & Data Formats

### Data Models (`com.sms.model`)

**BaseEntity** (Abstract)
- `int id`

**Course**
- `int courseId`
- `String courseName`
- `int credits`

**Student** (Extends `BaseEntity`)
- `String name`
- `double gpa`
- `List<Course> courses`

**GraduateStudent** (Extends `Student`)
- `String thesisTopic`

---

## 2. Method Signatures

### Model Classes

**BaseEntity**
- `public int getId()`
- `public void setId(int id)`
- `public String toString()`

**Course**
- `public int getCourseId()`
- `public void setCourseId(int courseId)`
- `public String getCourseName()`
- `public void setCourseName(String courseName)`
- `public int getCredits()`
- `public void setCredits(int credits)`
- `public String toString()`

**Student**
- `public String getName()`
- `public void setName(String name)`
- `public double getGpa()`
- `public void setGpa(double gpa)`
- `public List<Course> getCourses()`
- `public void setCourses(List<Course> courses)`
- `public void addCourse(Course course)`
- `public double calculateGPA()`
- `public String toString()`

**GraduateStudent**
- `public String getThesisTopic()`
- `public void setThesisTopic(String thesisTopic)`
- `public double calculateGPA()` *(Overridden)*
- `public String toString()` *(Overridden)*

---

### Service Interfaces (`com.sms.service`)

**StudentService**
- `void addStudent(Student student)`
- `void deleteStudent(int id)`
- `void updateStudent(Student student)`
- `Student findStudentById(int id)`

**CourseService**
- `void addCourse(Course course)`
- `void assignCourse(Student student, Course course)`

## 3. File Data Format (for io module)

- `students.txt:`
- `id,name,gpa`

- `courses.txt:`
- `courseId,courseName,credits`

## Rules:
- `Values are comma-separated`
- `No extra spaces`
- `One record per line`

## 4. Behavior Rules

- `Student stores enrolled courses using List<Course>`
- `addCourse(Course course):`
  - `Adds course to student's course list`
  - `Must not allow duplicates`

- `calculateGPA():`
  - `Returns a double`
  - `GPA logic will be implemented by the util module`

- `assignCourse(Student, Course):`
  - `Must call student.addCourse(course)` 

  ## 5. Exception Rules

- `findStudentById(int id):`
  - `Should throw StudentNotFoundException if not found`

- `GPA and grades:`
  - `Invalid values should throw InvalidGradeException`

  ## 6. Usage Example

- `Student s = new Student();`
- `s.setId(1);`
- `s.setName("Ali");`

- `Course c = new Course();`
- `c.setCourseId(101);`
- `c.setCourseName("Math");`
- `c.setCredits(3);`

- `s.addCourse(c);`
- `double gpa = s.calculateGPA();`

## 7. Naming Conventions

- `Method names must not be changed`
- `Use camelCase for methods`
- `Use exact names as defined in this document`
