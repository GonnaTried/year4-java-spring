// src/main/java/com/example/demo/model/Student.java
package com.bunnet.year4.model;

import java.util.Objects;

public class Student {

    private Long id;
    private String name;
    private int age;
    private String gender;

    // Constructor
    public Student(Long id, String name, int age, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    // Default constructor needed for Jackson
    public Student() {
    }

    // Getters (REQUIRED for Jackson serialization)
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Add these getter methods!
    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    // Setters (optional if you only need GET, required for POST/PUT to deserialize)
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) { // Add setter if needed for POST/PUT
        this.age = age;
    }

    public void setGender(String gender) { // Add setter if needed for POST/PUT
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        // Include age and gender in equality check
        return age == student.age
                && Objects.equals(id, student.id)
                && Objects.equals(name, student.name)
                && Objects.equals(gender, student.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, gender);
    }

    @Override
    public String toString() {
        return "Student{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", age=" + age
                + ", gender='" + gender + '\''
                + // Include new fields in toString
                '}';
    }
}
