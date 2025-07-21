package com.bunnet.year4.model;

import java.util.Objects;          // Import for @Entity

import jakarta.persistence.Entity;  // Import for @GeneratedValue
import jakarta.persistence.GeneratedValue;  // Import for GenerationType
import jakarta.persistence.GenerationType;              // Import for @Id
import jakarta.persistence.Id;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private String gender;
    private int score;

    public Student(Long id, String name, int age, String gender, int score) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.score = score;
    }

    public Student() {
    }

    // --- Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public int getScore() {
        return score;
    }

    // --- Setters ---
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // --- Overridden Methods ---
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return age == student.age
                && score == student.score
                && Objects.equals(id, student.id)
                && Objects.equals(name, student.name)
                && Objects.equals(gender, student.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, gender, score);
    }

    @Override
    public String toString() {
        return "Student{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", age=" + age
                + ", gender='" + gender + '\''
                + ", score=" + score
                + '}';
    }
}
