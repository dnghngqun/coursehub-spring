package org.example.coursehub.service;
import org.example.coursehub.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface CourseService {
    Page<Course> search(String q, Long categoryId, Pageable pageable);
    Course getBySlug(String slug);
    Course get(Long id);
    Course findBySlug(String slug);
    Course findById(Long id);
    long getTotalCourses();
    Course create(String title, Long categoryId, Long instructorId, String shortDesc, String content, String price, MultipartFile thumbnail);
    Course update(Long id, String title, Long categoryId, Long instructorId, String shortDesc, String content, String price, MultipartFile thumbnail);
    void delete(Long id);
}
