package com.librarymanagementsystem.project.controller;

import com.librarymanagementsystem.project.Dtos.UserDto;
import com.librarymanagementsystem.project.entity.BorrowEntity;
import com.librarymanagementsystem.project.entity.UserEntity;
import com.librarymanagementsystem.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    // Register new user
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        String result = userService.userCreation(userDto);
        if (result.equals("User Added Successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    // Get user details by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable Long id) {
        UserDto userDto = userService.userDto(id);
        return ResponseEntity.ok(userDto);
    }

    // Get borrowed books by user ID
    @GetMapping("/{id}/borrowed")
    public ResponseEntity<List<BorrowEntity>> getBorrowedDetails(@PathVariable Long id) {
        List<BorrowEntity> borrowed = userService.getBorrowedDetails(id);
        return ResponseEntity.ok(borrowed);
    }

    // Login and return JWT token
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserEntity userEntity) {
        String token = userService.verify(userEntity, authenticationManager);
        if (!token.equals("fail")) {
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
