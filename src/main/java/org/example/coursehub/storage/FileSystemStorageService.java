package org.example.coursehub.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileSystemStorageService implements StorageService {
    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        try { Files.createDirectories(rootLocation); } catch (IOException ignored) {}
    }

    @Override
    public String store(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String ext = "";
        int dot = filename.lastIndexOf('.');
        if (dot >= 0) ext = filename.substring(dot);
        String stored = UUID.randomUUID() + ext;
        try {
            Files.copy(file.getInputStream(), rootLocation.resolve(stored), StandardCopyOption.REPLACE_EXISTING);
            return stored;
        } catch (IOException e) { throw new RuntimeException("Failed to store file", e); }
    }

    @Override
    public Path load(String filename) { return rootLocation.resolve(filename); }

    @Override
    public org.springframework.core.io.Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            UrlResource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) { return resource; }
            else { throw new RuntimeException("Could not read file: " + filename); }
        } catch (MalformedURLException e) { throw new RuntimeException("Could not read file: " + filename, e); }
    }
}