# API Contract — Student Management System

> **Version:** 2.0 · Spring Boot · File-based persistence · Port `8080`

---

## 1. Model Layer (`com.sms.model`)

### `BaseEntity` *(abstract)*
| Field | Type | Notes |
|-------|------|-------|
| `id` | `int` | Auto-assigned numeric database ID |
| `createdAt` | `LocalDateTime` | Set on construction |
| `updatedAt` | `LocalDateTime` | Updated via `touch()` on every mutation |

**Key methods:** `getSummary(): String` *(abstract)*, `touch(): void`, standard getters/setters.

---

### `Student` extends `BaseEntity`
| Field | Type | Example |
|-------|------|---------|
| `studentId` | `String` | `"STU-001"` |
| `firstName` | `String` | `"Ali"` |
| `lastName` | `String` | `"Hassan"` |
| `email` | `String` | `"ali@example.com"` |
| `dateOfBirth` | `String` | `"2000-05-15"` |
| `status` | `String` | `ACTIVE` · `INACTIVE` · `GRADUATED` |
| `gpa` | `double` | 0.0 – 4.0 scale |
| `grades` | `List<Double>` | Raw 0–100 scores |

**Key methods:** `getSummary()`, `recalculateGpa()`, `toString()`

---

### `GraduateStudent` extends `Student`
| Field | Type |
|-------|------|
| `researchTopic` | `String` |
| `supervisorName` | `String` |

**Key methods:** `getSummary()` *(overrides Student)*, `toString()`

---

### `Course` extends `BaseEntity`
| Field | Type | Example |
|-------|------|---------|
| `courseId` | `String` | `"CS-101"` |
| `courseName` | `String` | `"Data Structures"` |
| `courseCode` | `String` | `"CS101"` |
| `credits` | `int` | `3` |
| `instructorName` | `String` | `"Dr. Smith"` |

**Key methods:** `getSummary()`, `toString()`

---

### `Enrollment` extends `BaseEntity`
| Field | Type | Notes |
|-------|------|-------|
| `id` (via BaseEntity) | `int` | Auto-assigned enrollment ID |
| `studentDbId` | `int` | References `Student.id` |
| `courseDbId` | `int` | References `Course.id` |
| `enrollmentDate` | `String` | ISO date, e.g. `"2026-04-17"` |
| `grade` | `double` | `0–100`; `-1` = not yet assigned |

**Key methods:** `getSummary()`, `toString()`, `getEnrollmentId()` *(alias for `getId()`)*

---

## 2. Service Interfaces (`com.sms.service`)

### `StudentService`
```
Student    addStudent(Student student)
Student    updateStudent(int id, Student student)          throws StudentNotFoundException
void       deleteStudent(int id)                           throws StudentNotFoundException
List<Student> getAllStudents()
Student    findStudentById(int id)                         throws StudentNotFoundException
Student    findStudentByCode(String studentId)             throws StudentNotFoundException
Student    findStudentByIdRecursive(List<Student>, int id, int index)  throws StudentNotFoundException
List<Student> sortByGpa()
void       loadStudents(List<Student> loaded)
List<Student> getStudentsRef()
```

### `CourseService`
```
Course    addCourse(Course course)
Course    updateCourse(int id, Course course)    throws CourseNotFoundException
void      deleteCourse(int id)                  throws CourseNotFoundException
List<Course> getAllCourses()
Course    findCourseById(int id)                throws CourseNotFoundException
void      loadCourses(List<Course> loaded)
List<Course> getCoursesRef()
```

### `EnrollmentService`
```
Enrollment   enrollStudent(int studentDbId, int courseDbId)     throws StudentNotFoundException, CourseNotFoundException
Enrollment   assignGrade(int enrollmentId, double grade)        throws InvalidGradeException, StudentNotFoundException
List<Enrollment> getStudentCourses(int studentDbId)
List<Enrollment> getAllEnrollments()
void             loadEnrollments(List<Enrollment> loaded)
```

---

## 3. REST Endpoints

### Students — `/api/students`

| Method | Path | Description | Status |
|--------|------|-------------|--------|
| `GET` | `/api/students` | List all students | `200 OK` |
| `GET` | `/api/students/{id}` | Find by numeric DB id (recursive search) | `200` / `404` |
| `GET` | `/api/students/code/{studentId}` | Find by student code e.g. `STU-001` | `200` / `404` |
| `GET` | `/api/students/sorted` | All students sorted by GPA descending | `200 OK` |
| `POST` | `/api/students` | Add student | `201 Created` |
| `PUT` | `/api/students/{id}` | Update student | `200` / `404` |
| `DELETE` | `/api/students/{id}` | Delete student | `204 No Content` / `404` |

**POST / PUT request body (regular student):**
```json
{
  "studentId": "STU-001",
  "firstName": "Ali",
  "lastName": "Hassan",
  "email": "ali@example.com",
  "dateOfBirth": "2000-05-15",
  "status": "ACTIVE",
  "type": "student"
}
```

**POST / PUT request body (graduate student):**
```json
{
  "studentId": "GRD-001",
  "firstName": "Sara",
  "lastName": "Khan",
  "email": "sara@example.com",
  "dateOfBirth": "1998-03-20",
  "status": "ACTIVE",
  "researchTopic": "Machine Learning",
  "supervisorName": "Dr. Ahmed",
  "type": "graduate"
}
```

---

### Courses — `/api/courses`

| Method | Path | Description | Status |
|--------|------|-------------|--------|
| `GET` | `/api/courses` | List all courses | `200 OK` |
| `GET` | `/api/courses/{id}` | Find by numeric DB id | `200` / `404` |
| `POST` | `/api/courses` | Add course | `201 Created` |
| `PUT` | `/api/courses/{id}` | Update course | `200` / `404` |
| `DELETE` | `/api/courses/{id}` | Delete course | `204 No Content` / `404` |

**POST / PUT request body:**
```json
{
  "courseId": "CS-101",
  "courseName": "Data Structures",
  "courseCode": "CS101",
  "credits": 3,
  "instructorName": "Dr. Smith"
}
```

---

### Enrollments — `/api/enrollments`

| Method | Path | Description | Status |
|--------|------|-------------|--------|
| `GET` | `/api/enrollments` | List all enrollments | `200 OK` |
| `GET` | `/api/enrollments/student/{studentDbId}` | Enrollments for student | `200 OK` |
| `POST` | `/api/enrollments` | Enroll student in course | `201 Created` |
| `PUT` | `/api/enrollments/{enrollmentId}/grade` | Assign grade | `200 OK` |

**POST enroll body:**
```json
{ "studentDbId": 1, "courseDbId": 2 }
```

**PUT grade body:**
```json
{ "grade": 87.5 }
```

---

### Auth — `/api/auth`

| Method | Path | Description | Status |
|--------|------|-------------|--------|
| `POST` | `/api/auth/login` | Authenticate admin | `200` / `401` |

**POST /login request body:**
```json
{
  "username": "ucanaryn",
  "password": "naryncampus"
}
```

---

## 4. GPA Grading Scale

The following university-standard scale is applied in `Student.recalculateGpa()`:

| Average Score | GPA | Grade |
|---------------|-----|-------|
| 90 – 100 | 4.0 | A |
| 85 – 89.9 | 3.7 | A- |
| 80 – 84.9 | 3.3 | B+ |
| 75 – 79.9 | 3.0 | B |
| 70 – 74.9 | 2.7 | B- |
| 65 – 69.9 | 2.3 | C+ |
| 60 – 64.9 | 2.0 | C |
| 55 – 59.9 | 1.7 | C- |
| 50 – 54.9 | 1.0 | D |
| 0 – 49.9 | 0.0 | F |

---

## 5. Error Response Format

All errors are handled by `GlobalExceptionHandler` (`@ControllerAdvice`) and return:

```json
{
  "timestamp": "2026-04-17T16:30:00.123",
  "status": 404,
  "error": "Not Found",
  "message": "Student not found with id: 99"
}
```

| Exception | HTTP Status |
|-----------|-------------|
| `StudentNotFoundException` | `404 Not Found` |
| `CourseNotFoundException` | `404 Not Found` |
| `InvalidGradeException` | `400 Bad Request` |
| Any other `Exception` | `500 Internal Server Error` |

---

## 5. File Persistence Format

Files are stored in `backend/data/` (configured via `application.properties`).

**`students.txt`** — pipe-delimited, one record per line:
```
type|id|studentId|firstName|lastName|email|dateOfBirth|status|gpa|grades|researchTopic|supervisorName
student|1|STU-001|Ali|Hassan|ali@email.com|2000-05-15|ACTIVE|3.5|87.5,90.0||
graduate|2|GRD-001|Sara|Khan|sara@email.com|1998-03-20|ACTIVE|3.8|95.0|Machine Learning|Dr. Ahmed
```

**`courses.txt`** — pipe-delimited:
```
id|courseId|courseName|courseCode|credits|instructorName
1|CS-101|Data Structures|CS101|3|Dr. Smith
```

**`enrollments.txt`** — pipe-delimited:
```
id|studentDbId|courseDbId|enrollmentDate|grade
1|1|1|2026-04-17|87.5
```

---

## 6. OOP Concepts Demonstrated

| Concept | Location |
|---------|----------|
| **Abstraction** | `BaseEntity.getSummary()` is abstract |
| **Encapsulation** | All fields private, accessed via getters/setters |
| **Inheritance** | `Student → BaseEntity`, `GraduateStudent → Student`, `Course → BaseEntity`, `Enrollment → BaseEntity` |
| **Polymorphism** | Controllers inject service interfaces; `getSummary()` overridden at each level |
| **Recursion** | `StudentServiceImpl.findStudentByIdRecursive()` used in real operations |
| **Exception Handling** | `GlobalExceptionHandler` catches all custom exceptions → structured JSON |
| **File I/O** | `FileManager` uses `BufferedReader`/`BufferedWriter` for all persistence |
