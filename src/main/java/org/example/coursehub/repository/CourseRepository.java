package org.example.coursehub.repository;

import org.example.coursehub.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findBySlug(String slug);

    @Query("""
   select c from Course c
   where (:q is null or lower(c.title) like lower(concat('%',:q,'%')))
     and (:categoryId is null or c.category.id = :categoryId)
  """)
    Page<Course> search(@Param("q") String q, @Param("categoryId") Long categoryId, Pageable pageable);
}