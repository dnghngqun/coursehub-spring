package org.example.coursehub.service.impl;

import org.example.coursehub.entity.*;
import org.example.coursehub.repository.*;
import org.example.coursehub.service.CourseService;
import org.example.coursehub.storage.StorageService;
import org.example.coursehub.util.SlugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Service @RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepo;
    private final CategoryRepository categoryRepo;
    private final InstructorRepository instructorRepo;
    private final StorageService storageService;

    @Override public Page<Course> search(String q, Long categoryId, Pageable pageable) {
        return courseRepo.search(q, categoryId, pageable);
    }

    @Override public Course getBySlug(String slug) { return courseRepo.findBySlug(slug).orElseThrow(); }
    @Override public Course get(Long id) { return courseRepo.findById(id).orElseThrow(); }
    @Override public Course findBySlug(String slug) { return getBySlug(slug); }
    @Override public Course findById(Long id) { return get(id); }
    @Override public long getTotalCourses() { return courseRepo.count(); }

    @Override public Course create(String title, Long categoryId, Long instructorId, String shortDesc, String content, String price, MultipartFile thumbnail) {
        Category cat = categoryRepo.findById(categoryId).orElseThrow();
        Instructor ins = instructorRepo.findById(instructorId).orElseThrow();
        String slug = SlugUtil.slugify(title);
        String stored = thumbnail != null ? storageService.store(thumbnail) : null;
        Course c = Course.builder()
                .title(title).slug(slug)
                .category(cat).instructor(ins)
                .shortDesc(shortDesc).content(content)
                .price(price == null || price.isBlank() ? null : new BigDecimal(price))
                .thumbnailPath(stored)
                .build();
        return courseRepo.save(c);
    }

    @Override public Course update(Long id, String title, Long categoryId, Long instructorId, String shortDesc, String content, String price, MultipartFile thumbnail) {
        Course c = get(id);
        c.setTitle(title);
        c.setSlug(SlugUtil.slugify(title));
        c.setCategory(categoryRepo.findById(categoryId).orElseThrow());
        c.setInstructor(instructorRepo.findById(instructorId).orElseThrow());
        c.setShortDesc(shortDesc);
        c.setContent(content);
        c.setPrice(price == null || price.isBlank() ? null : new BigDecimal(price));
        if (thumbnail != null && !thumbnail.isEmpty()) c.setThumbnailPath(storageService.store(thumbnail));
        return courseRepo.save(c);
    }

    @Override public void delete(Long id) { courseRepo.deleteById(id); }
}