package com.librarymanagementsystem.project.Dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookDto {
    private Long bookId;
    private String title;
    private String author;
    private String category;
    private Long totalCopies;
    private Long availableCopies;
    private LocalDateTime createdAt;
    private Boolean isActive;

}
