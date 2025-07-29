package com.bunnet.year4.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunnet.year4.model.Student;
import com.bunnet.year4.service.StudentService;

@RestController // Marks this class as a REST Controller
@RequestMapping("/api/v1/students") // Sets the base path for all endpoints in this controller
public class StudentController {

    private final StudentService studentService;

    // Inject the StudentService dependency
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        // Return 200 OK with student body if found, else 404 Not Found
        return student.map(ResponseEntity::ok) // If student is present, return 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build()); // If not present, return 404 Not Found
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        // Return 201 Created status with the newly created student in the body
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        // The service method handles finding and updating.
        // We pass the ID from the path and the updated data from the request body.
        Optional<Student> updatedStudent = studentService.updateStudent(id, studentDetails);

        // Return 200 OK with updated student if found, else 404 Not Found
        return updatedStudent.map(ResponseEntity::ok) // If updatedStudent is present, return 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build()); // If not present, return 404 Not Found
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {

        studentService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }
}
