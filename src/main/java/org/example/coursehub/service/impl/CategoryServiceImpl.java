package org.example.coursehub.service.impl;

import org.example.coursehub.entity.Category;
import org.example.coursehub.repository.CategoryRepository;
import org.example.coursehub.service.CategoryService;
import org.example.coursehub.util.SlugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repo;

    @Override public List<Category> findAll() { return repo.findAll(); }
    @Override public Category get(Long id) { return repo.findById(id).orElseThrow(); }
    @Override public Category create(String name) {
        Category c = Category.builder().name(name).slug(SlugUtil.slugify(name)).build();
        return repo.save(c);
    }
    @Override public Category update(Long id, String name) {
        Category c = get(id); c.setName(name); c.setSlug(SlugUtil.slugify(name)); return repo.save(c);
    }
    @Override public void delete(Long id) { repo.deleteById(id); }
}