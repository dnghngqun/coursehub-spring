package org.example.coursehub.service;
import org.example.coursehub.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category get(Long id);
    Category create(String name);
    Category update(Long id, String name);
    void delete(Long id);
}