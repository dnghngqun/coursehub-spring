package org.example.coursehub.service.impl;

import org.example.coursehub.entity.Instructor;
import org.example.coursehub.repository.InstructorRepository;
import org.example.coursehub.service.InstructorService;
import org.example.coursehub.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service @RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {
    private final InstructorRepository repo;
    private final StorageService storage;

    @Override public List<Instructor> findAll() { return repo.findAll(); }
    @Override public Instructor get(Long id) { return repo.findById(id).orElseThrow(); }

    @Override public Instructor create(String name, String email, String bio, MultipartFile avatar) {
        String stored = avatar != null ? storage.store(avatar) : null;
        Instructor i = Instructor.builder().name(name).email(email).bio(bio).avatar(stored).build();
        return repo.save(i);
    }

    @Override public Instructor update(Long id, String name, String email, String bio, MultipartFile avatar) {
        Instructor i = get(id);
        i.setName(name); i.setEmail(email); i.setBio(bio);
        if (avatar != null && !avatar.isEmpty()) i.setAvatar(storage.store(avatar));
        return repo.save(i);
    }

    @Override public void delete(Long id) { repo.deleteById(id); }
}