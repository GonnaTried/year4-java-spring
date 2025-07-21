package com.bunnet.year4.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bunnet.year4.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByScore(int score);
}
