package org.example.coursehub;

import org.example.coursehub.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CourseHubApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseHubApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return args -> storageService.init();
    }
}