package com.librarymanagementsystem.project.Dtos;

import com.librarymanagementsystem.project.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long userid;
    private String Password;
    private String name;
    private String email;
    private UserRole userrole;
    private int maxBooksAllowed;
    private int borrowedBooks;
}
