package com.librarymanagementsystem.project.Dtos;

import com.librarymanagementsystem.project.enums.BorrowStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BorrowDto {
    private Long userId;
    private Long bookId;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private BorrowStatus borrowStatus;
    private Double fineAmount;
}
