package com.bunnet.year4.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunnet.year4.model.Student;

@RestController
@RequestMapping("/api/students") // Added a base path for clarity
public class StudentController {

    private final List<Student> students = new ArrayList<>();

    // Constructor - Populating the list here
    public StudentController() {
        students.add(new Student(1L, "Dara", 20, "Male"));
        students.add(new Student(2L, "Dany", 21, "Female"));
        students.add(new Student(3L, "Maya", 25, "Female"));

    }

    // GET /api/students
    @GetMapping
    public List<Student> getAllStudents() {
        return students;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> studentOptional = students.stream()
                .filter(student -> student.getId().equals(id)) // Use .equals for object comparison
                .findFirst(); // findFirst returns Optional<Student>

        return studentOptional.map(student -> ResponseEntity.ok(student)) // If Optional has a value, return OK
                .orElseGet(() -> ResponseEntity.notFound().build()); // If Optional is empty, return 404
    }
}
