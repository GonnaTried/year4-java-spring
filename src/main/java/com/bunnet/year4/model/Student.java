// src/main/java/com/bunnet/year4/model/Student.java
package com.bunnet.year4.model;

import java.util.Objects;

public class Student {

    private Long id;
    private String name;
    private String gender;
    private String email; // Added 'private' keyword - good practice
    private int score;   // Added 'private' keyword - good practice

    // --- Constructors ---
    // Constructor with all fields (RECOMMENDED if creating students with initial data)
    public Student(Long id, String name, String gender, String email, int score) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.score = score;
    }

    // Default constructor needed for Jackson (JSON serialization/deserialization)
    public Student() {
    }

    // --- Getters (REQUIRED for Jackson serialization) ---
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    // --- New Getters for email and score ---
    public String getEmail() {
        return email;
    }

    public int getScore() {
        return score;
    }

    // --- Setters (REQUIRED for Jackson deserialization when receiving JSON requests) ---
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // --- New Setters for email and score ---
    public void setEmail(String email) {
        this.email = email;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // --- equals(), hashCode(), toString() ---
    // NOTE: These methods in your provided snippet do *not* include email and score.
    // For a complete and correct model, you should regenerate or manually update them
    // to include all relevant fields (id, name, age, gender, email, score).
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return score == student.score // Added score
                && Objects.equals(id, student.id)
                && Objects.equals(name, student.name)
                && Objects.equals(gender, student.gender)
                && Objects.equals(email, student.email); // Added email
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, gender, email, score); // Added email, score
    }

    @Override
    public String toString() {
        return "Student{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", gender='" + gender + '\''
                + ", email='" + email + '\'' // Added email
                + ", score=" + score // Added score
                + '}';
    }
}
