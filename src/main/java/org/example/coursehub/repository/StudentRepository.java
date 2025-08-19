package org.example.coursehub.repository;

import org.example.coursehub.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);

    @Query("SELECT s FROM Student s WHERE " +
           "(:q IS NULL OR s.name ILIKE %:q% OR s.email ILIKE %:q%)")
    Page<Student> findBySearchQuery(@Param("q") String q, Pageable pageable);
}
