package org.example.coursehub.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.coursehub.entity.*;
import org.example.coursehub.repository.EnrollmentRepository;
import org.example.coursehub.service.EnrollmentService;
import org.example.coursehub.service.StudentService;
import org.example.coursehub.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentService studentService;
    private final CourseService courseService;

    @Override
    public Page<Enrollment> findAll(EnrollmentStatus status, String q, Pageable pageable) {
        return enrollmentRepository.findByStatusAndSearchQuery(status, q, pageable);
    }

    @Override
    public Enrollment findById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
    }

    @Override
    public Enrollment enroll(String studentEmail, String studentName, String studentPhone, Long courseId) {
        // Find or create student
        Student student = studentService.findByEmail(studentEmail);
        if (student == null) {
            student = Student.builder()
                    .name(studentName)
                    .email(studentEmail)
                    .phone(studentPhone)
                    .build();
            student = studentService.save(student);
        }

        // Check if already enrolled
        if (enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), courseId)) {
            throw new RuntimeException("Học viên đã đăng ký khóa học này");
        }

        Course course = courseService.findById(courseId);

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .status(EnrollmentStatus.PENDING)
                .build();

        return enrollmentRepository.save(enrollment);
    }

    @Override
    public Enrollment updateStatus(Long id, EnrollmentStatus status, String notes) {
        Enrollment enrollment = findById(id);
        enrollment.setStatus(status);
        enrollment.setNotes(notes);
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public void deleteById(Long id) {
        enrollmentRepository.deleteById(id);
    }

    @Override
    public long getTotalEnrollments() {
        return enrollmentRepository.count();
    }

    @Override
    public long getTodayEnrollments() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        return enrollmentRepository.countByEnrolledAtAfter(startOfDay);
    }
}