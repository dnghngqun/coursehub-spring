package org.example.coursehub.service;

import org.example.coursehub.entity.Enrollment;
import org.example.coursehub.entity.EnrollmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EnrollmentService {
    Page<Enrollment> findAll(EnrollmentStatus status, String q, Pageable pageable);
    Enrollment findById(Long id);
    Enrollment enroll(String studentEmail, String studentName, String studentPhone, Long courseId);
    Enrollment updateStatus(Long id, EnrollmentStatus status, String notes);
    void deleteById(Long id);
    long getTotalEnrollments();
    long getTodayEnrollments();
}
