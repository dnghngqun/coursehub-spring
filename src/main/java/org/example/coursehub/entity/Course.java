package org.example.coursehub.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false, unique = true)
    private String slug;

    private BigDecimal price;
    private String thumbnailPath; // stored filename
    private String shortDesc;

    @Lob
    private String content; // long description / HTML

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    private Instructor instructor;

    @CreationTimestamp
    private Instant createdAt;
}