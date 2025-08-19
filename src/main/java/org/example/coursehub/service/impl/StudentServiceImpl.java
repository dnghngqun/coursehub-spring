package org.example.coursehub.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.coursehub.entity.Student;
import org.example.coursehub.repository.StudentRepository;
import org.example.coursehub.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public Page<Student> findAll(String q, Pageable pageable) {
        return studentRepository.findBySearchQuery(q, pageable);
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    public Student save(Student student) {
        if (student.getId() == null && studentRepository.existsByEmail(student.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return studentRepository.save(student);
    }

    @Override
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Student findByEmail(String email) {
        return studentRepository.findAll().stream()
                .filter(s -> s.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}