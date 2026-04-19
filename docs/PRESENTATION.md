# Project Presentation: Student Management System

**Target Time:** 15 – 20 Minutes
**Focus:** OOP, Interface, Exception Handling, File I/O, Recursion.
(Spring Boot and REST APIs are treated as "Infrastructure" to keep focus on core Java concepts).

---

## 1. Introduction (2-3 Minutes)
*   **Slide 1: Title Slide & Team**
*   **Slide 2: System Overview**
    *   Goal: Create a robust system to manage students, courses, and enrollments.
    *   Key Requirements: Persistent storage, advanced searching, and a strong object-oriented design.

---

## 2. Architecture and Class UML (4-5 Minutes)
*   **Slide 3: Technical UML Diagram**
    *   Explain the hierarchy: `BaseEntity` -> `Student` -> `GraduateStudent`.
    *   Explain the relationships: `Enrollment` links `Student` and `Course`.
    *   Explain the Service Layer: How interfaces like `StudentService` define the "what" and `StudentServiceImpl` defines the "how".
*   **Key Point:** This structure ensures scalability and code reusability through Inheritance and Abstraction.

---

## 3. Core Object-Oriented Principles (4 Minutes)
*   **Slide 4: Abstraction & Inheritance**
    *   `BaseEntity` is abstract. It enforces that every entity must have a `getSummary()` method.
    *   `GraduateStudent` extends `Student`, adding research-specific fields while keeping all basic student data.
*   **Slide 5: Encapsulation & Polymorphism**
    *   All data fields are `private`. We use getters/setters (Encapsulation).
    *   Polymorphism is shown when we call `getSummary()` on a list of students; different objects (Regular vs. Graduate) behave differently.

---

## 4. Advanced Java Implementation (5 Minutes)
*   **Slide 6: Exception Handling**
    *   We created custom exceptions like `StudentNotFoundException`.
    *   `GlobalExceptionHandler` ensures all errors are caught and returned as clean messages.
*   **Slide 7: Recursion**
    *   Instead of a simple loop, we implemented `findStudentByIdRecursive` in `StudentServiceImpl`.
    *   Explain the Base Case (found or not in list) and the Recursive Step (move to next index).
*   **Slide 8: Utility Classes & File I/O**
    *   `ActivityLogger`: Automatically logs every student addition/update for audit purposes.
    *   `FileManager`: Uses `BufferedReader` and `BufferedWriter` to save/load from pipe-delimited text files.

---

## 5. Conclusion & Q&A (2 Minutes)
*   **Slide 9: Summary**
    *   Successfully applied core OOP concepts.
    *   Robust architecture ready for future features.

---

## Script Highlights (for the speaker):
*   *"By using an abstract BaseEntity, we ensure a unified identity for every object in our system..."*
*   *"Polymorphism allows our Dashboard to display summaries for both regular and graduate students without knowing their specific types..."*
*   *"Our recursion logic demonstrates a deep understanding of stack-based algorithmic thinking..."*
