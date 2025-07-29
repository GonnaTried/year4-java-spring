package com.bunnet.year4.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bunnet.year4.model.Student;
import com.bunnet.year4.repository.StudentRepository;
import com.bunnet.year4.exception.ResourceNotFoundException;

@Service // Marks this class as a Spring Service component
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Get all students.
     *
     * @return List of all students.
     */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * Get a student by ID.
     *
     * @param id The ID of the student.
     * @return An Optional containing the student if found.
     */
    public Optional<Student> getStudentById(Long id) {
        return Optional.ofNullable(studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id)));
    }

    /**
     * Create a new student.
     *
     * @param student The student object to create (ID should typically be
     * null).
     * @return The created student object with its assigned ID.
     */
    public Student createStudent(Student student) {
        // Ensure the ID is null for a new student creation
        student.setId(null);
        return studentRepository.save(student);
    }

    /**
     * Update an existing student.
     *
     * @param id The ID of the student to update.
     * @param updatedStudentDetails The student object with updated data.
     * @return An Optional containing the updated student if found, otherwise
     * empty.
     */
    public Optional<Student> updateStudent(Long id, Student updatedStudentDetails) {
        Optional<Student> studentOptional = studentRepository.findById(id);

        if (studentOptional.isPresent()) {
            Student existingStudent = studentOptional.get();
            // Update the fields of the existing student object
            existingStudent.setName(updatedStudentDetails.getName());
            existingStudent.setGender(updatedStudentDetails.getGender());
            existingStudent.setEmail(updatedStudentDetails.getEmail());
            existingStudent.setScore(updatedStudentDetails.getScore());

            // Pass the modified existing student object back to repository save
            // The repository's save method handles the logic for updating based on ID
            return Optional.of(studentRepository.save(existingStudent));
        } else {
            return Optional.ofNullable(studentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("No student with id: " + id + " to update")));
        }
    }

    /**
     * Delete a student by ID.
     *
     * @param id The ID of the student to delete.
     * @return true if the student was found and deleted, false otherwise.
     */
    public void deleteStudent(Long id) {

        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student with ID: " + id + " not found for deletion.");
        }

        studentRepository.deleteById(id);

    }
}
