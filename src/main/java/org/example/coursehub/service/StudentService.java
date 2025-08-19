package org.example.coursehub.service;

import org.example.coursehub.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {
    Page<Student> findAll(String q, Pageable pageable);

    Student findById(Long id);

    Student save(Student student);

    void deleteById(Long id);

    Student findByEmail(String email);
}
