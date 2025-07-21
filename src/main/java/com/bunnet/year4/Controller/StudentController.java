package com.bunnet.year4.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired; // Import for @Autowired
import org.springframework.http.HttpStatus; // Import for HTTP status codes
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping; // Import for @DeleteMapping
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; // Import for @PostMapping
import org.springframework.web.bind.annotation.PutMapping; // Import for @PutMapping
import org.springframework.web.bind.annotation.RequestBody; // Import for @RequestBody
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunnet.year4.model.Student;
import com.bunnet.year4.repository.StudentRepository; // Import your StudentRepository

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        // No need for hardcoded student data here anymore, it will come from the DB
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {

        Student savedStudent = studentRepository.save(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED); // Return 201 Created
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll(); // Use JpaRepository's findAll method
    }

    // GET /api/students/{id}
    // Fetches a single student by ID from the database
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        // Use JpaRepository's findById method, which returns an Optional
        Optional<Student> studentOptional = studentRepository.findById(id);

        return studentOptional.map(student -> ResponseEntity.ok(student)) // If Optional has a value, return 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build()); // If Optional is empty, return 404 Not Found
    }

    // PUT /api/students/{id}
    // Updates an existing student in the database
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        Optional<Student> studentOptional = studentRepository.findById(id);

        if (studentOptional.isPresent()) {
            Student existingStudent = studentOptional.get();
            // Update fields from the request body. Ensure all fields you want to update are set.
            existingStudent.setName(studentDetails.getName());
            existingStudent.setAge(studentDetails.getAge());
            existingStudent.setGender(studentDetails.getGender());
            existingStudent.setScore(studentDetails.getScore()); // Update the score

            Student updatedStudent = studentRepository.save(existingStudent); // Save the updated student
            return ResponseEntity.ok(updatedStudent); // Return 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if student doesn't exist
        }
    }

    // DELETE /api/students/{id}
    // Deletes a student from the database
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);

        if (studentOptional.isPresent()) {
            studentRepository.delete(studentOptional.get()); // Delete the student
            return ResponseEntity.noContent().build(); // Return 204 No Content for successful deletion
        } else {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if student doesn't exist
        }
    }
}
