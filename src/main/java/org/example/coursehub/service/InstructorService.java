package org.example.coursehub.service;
import org.example.coursehub.entity.Instructor;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface InstructorService {
    List<Instructor> findAll();
    Instructor get(Long id);
    Instructor create(String name, String email, String bio, MultipartFile avatar);
    Instructor update(Long id, String name, String email, String bio, MultipartFile avatar);
    void delete(Long id);
}