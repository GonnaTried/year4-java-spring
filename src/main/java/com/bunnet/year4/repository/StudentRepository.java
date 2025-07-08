package com.bunnet.year4.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.bunnet.year4.model.Student;

@Repository
public class StudentRepository {

    // In-memory list to store students (simulating a database)
    private final List<Student> students = new ArrayList<>();
    // AtomicLong for generating unique IDs
    private final AtomicLong counter = new AtomicLong();

    public StudentRepository() {
        students.add(new Student(counter.incrementAndGet(), "Alice Smith", "Female", "AliceSmith@email.com", 85));
        students.add(new Student(counter.incrementAndGet(), "Bob Johnson", "Male", "BobJohnson@email.com", 92));
        students.add(new Student(counter.incrementAndGet(), "Charlie Brown", "Male", "CharlieBrown@email.com", 78));
        students.add(new Student(counter.incrementAndGet(), "David Lee", "Male", "DavidLee@email.com", 95));
        students.add(new Student(counter.incrementAndGet(), "Eva Davis", "Female", "EvaDavis@email.com", 88));

    }

    /**
     * Get all students.
     *
     * @return List of all students.
     */
    public List<Student> findAll() {
        return new ArrayList<>(students); // Return a copy to prevent external modification
    }

    /**
     * Find a student by their ID.
     *
     * @param id The ID of the student to find.
     * @return An Optional containing the student if found, otherwise empty.
     */
    public Optional<Student> findById(Long id) {
        return students.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst();
    }

    /**
     * Save a student. If the student has a null ID, it's treated as a new
     * student and assigned an ID. If the student has an ID, it attempts to
     * update an existing student with that ID.
     *
     * @param student The student object to save.
     * @return The saved or updated student object.
     */
    public Student save(Student student) {
        if (student.getId() == null) {
            // It's a new student - assign a new ID
            long newId = counter.incrementAndGet();
            student.setId(newId);
            students.add(student);
            return student;
        } else {
            // It's an existing student - find and update
            Optional<Student> existingStudentOpt = findById(student.getId());
            if (existingStudentOpt.isPresent()) {
                Student existingStudent = existingStudentOpt.get();
                // Update fields (excluding ID)
                existingStudent.setName(student.getName());
                existingStudent.setGender(student.getGender());
                existingStudent.setEmail(student.getEmail());
                existingStudent.setScore(student.getScore());
                return existingStudent; // Return the updated object
            } else {

                throw new IllegalArgumentException("Student with ID " + student.getId() + " not found for update.");
            }
        }
    }

    /**
     * Delete a student by their ID.
     *
     * @param id The ID of the student to delete.
     * @return true if a student was found and deleted, false otherwise.
     */
    public boolean deleteById(Long id) {
        return students.removeIf(student -> student.getId().equals(id));
    }
}
