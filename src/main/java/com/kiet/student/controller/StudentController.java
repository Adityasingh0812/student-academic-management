package com.kiet.student.controller;

import com.kiet.student.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * REST Controller for managing Student Academic information.
 * Supports CRUD operations, search by branch & year, and filtering by CGPA.
 */
@RestController
@RequestMapping("/students")
public class StudentController {

    // Thread-safe in-memory list for storing student data
    private final List<Student> studentList = new CopyOnWriteArrayList<>();

    /**
     * Constructor initializing in-memory list with at least 5 students (Question 3).
     */
    public StudentController() {
        studentList.add(new Student(101, "Rahul Sharma", "rahul@kiet.edu", "CSE(AI & ML)", 2, 8.65));
        studentList.add(new Student(102, "Priya Patel", "priya@kiet.edu", "CSE", 2, 9.10));
        studentList.add(new Student(103, "Aman Verma", "aman@kiet.edu", "CSE", 3, 8.40));
        studentList.add(new Student(104, "Sneha Gupta", "sneha@kiet.edu", "IT", 2, 9.25));
        studentList.add(new Student(105, "Rohit Kumar", "rohit@kiet.edu", "CSE", 2, 7.90));
    }

    /**
     * QUESTION 3A - Retrieve all students.
     * GET /students
     *
     * @return List of all students
     */
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentList);
    }

    /**
     * QUESTION 3B - Retrieve a particular student by studentId.
     * GET /students/{studentId}
     *
     * @param studentId ID of the student to retrieve
     * @return Student JSON if found, or {"message": "Student not found"} with 404
     */
    @GetMapping("/{studentId:[0-9]+}")
    public ResponseEntity<?> getStudentById(@PathVariable Integer studentId) {
        Optional<Student> student = studentList.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst();

        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Student not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * QUESTION 4 - Add a new student.
     * POST /students
     *
     * @param student Student object passed in JSON request body
     * @return Newly added student JSON with 201 Created
     */
    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        if (student == null || student.getStudentId() == null) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Invalid student data: studentId is required");
            return ResponseEntity.badRequest().body(error);
        }

        // Check if student with same ID already exists
        boolean exists = studentList.stream()
                .anyMatch(s -> s.getStudentId().equals(student.getStudentId()));
        if (exists) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Student with ID " + student.getStudentId() + " already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        studentList.add(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }

    /**
     * QUESTION 5A - Update an existing student record.
     * PUT /students/{studentId}
     *
     * @param studentId ID of student to update
     * @param updatedData New student information passed in JSON request body
     * @return Updated student JSON or {"message": "Student not found"} with 404
     */
    @PutMapping("/{studentId:[0-9]+}")
    public ResponseEntity<?> updateStudent(@PathVariable Integer studentId, @RequestBody Student updatedData) {
        for (Student student : studentList) {
            if (student.getStudentId().equals(studentId)) {
                if (updatedData.getName() != null) {
                    student.setName(updatedData.getName());
                }
                if (updatedData.getEmail() != null) {
                    student.setEmail(updatedData.getEmail());
                }
                if (updatedData.getBranch() != null) {
                    student.setBranch(updatedData.getBranch());
                }
                if (updatedData.getYear() != null) {
                    student.setYear(updatedData.getYear());
                }
                if (updatedData.getCgpa() != null) {
                    student.setCgpa(updatedData.getCgpa());
                }
                return ResponseEntity.ok(student);
            }
        }

        Map<String, String> error = new HashMap<>();
        error.put("message", "Student not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * QUESTION 5B - Delete a student record by studentId.
     * DELETE /students/{studentId}
     *
     * @param studentId ID of student to delete
     * @return {"message": "Student deleted successfully"} or {"message": "Student not found"}
     */
    @DeleteMapping("/{studentId:[0-9]+}")
    public ResponseEntity<?> deleteStudent(@PathVariable Integer studentId) {
        boolean removed = studentList.removeIf(s -> s.getStudentId().equals(studentId));

        Map<String, String> response = new HashMap<>();
        if (removed) {
            response.put("message", "Student deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Student not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * QUESTION 6A - Search students based on branch and academic year.
     * GET /students/search?branch=CSE&year=2
     *
     * @param branch Branch name (e.g., CSE)
     * @param year Academic year (e.g., 2)
     * @return List of matching students
     */
    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchByBranchAndYear(
            @RequestParam String branch,
            @RequestParam Integer year) {

        List<Student> result = studentList.stream()
                .filter(s -> s.getBranch() != null && s.getBranch().equalsIgnoreCase(branch.trim())
                        && s.getYear() != null && s.getYear().equals(year))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    /**
     * QUESTION 6B - Find students whose CGPA is greater than or equal to supplied CGPA.
     * GET /students/topper?cgpa=9.0
     *
     * @param cgpa Minimum CGPA threshold
     * @return List of students meeting or exceeding the specified CGPA
     */
    @GetMapping("/topper")
    public ResponseEntity<List<Student>> getStudentsByCgpa(
            @RequestParam Double cgpa) {

        List<Student> result = studentList.stream()
                .filter(s -> s.getCgpa() != null && s.getCgpa() >= cgpa)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}
