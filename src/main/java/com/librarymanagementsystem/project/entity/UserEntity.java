package com.librarymanagementsystem.project.entity;

import com.librarymanagementsystem.project.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="userEntity")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;
    private String name;
    private String email;
    @Enumerated (EnumType.STRING)
    private UserRole userrole;
    private int maxBooksAllowed;
    private int borrowedBooks;

}
