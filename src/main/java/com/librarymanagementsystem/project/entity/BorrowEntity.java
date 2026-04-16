package com.librarymanagementsystem.project.entity;

import com.librarymanagementsystem.project.enums.BorrowStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Entity
@Table(name="borrowEntity")
@Data
public class BorrowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long borrowId;
    private String userName;
    private String book;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    @Enumerated(EnumType.STRING)
    private BorrowStatus borrowStatus;
    private Double fineAmount;
}
