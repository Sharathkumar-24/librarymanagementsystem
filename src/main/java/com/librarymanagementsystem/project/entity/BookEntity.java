package com.librarymanagementsystem.project.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="bookEntity")
@Data
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String title;
    private String author;
    private String category;
    private Long totalCopies;
    private Long availableCopies;
    private LocalDateTime createdAt;
    private Boolean isActive;

}
