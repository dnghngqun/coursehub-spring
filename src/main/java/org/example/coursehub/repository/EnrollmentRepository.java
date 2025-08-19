package org.example.coursehub.repository;

import org.example.coursehub.entity.Enrollment;
import org.example.coursehub.entity.EnrollmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Query("SELECT e FROM Enrollment e JOIN FETCH e.student JOIN FETCH e.course WHERE " +
           "(:status IS NULL OR e.status = :status) AND " +
           "(:q IS NULL OR e.student.name ILIKE %:q% OR e.course.title ILIKE %:q%)")
    Page<Enrollment> findByStatusAndSearchQuery(@Param("status") EnrollmentStatus status,
                                               @Param("q") String q,
                                               Pageable pageable);

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    long countByStatus(EnrollmentStatus status);

    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.enrolledAt >= :startDate")
    long countByEnrolledAtAfter(@Param("startDate") LocalDateTime startDate);
}
