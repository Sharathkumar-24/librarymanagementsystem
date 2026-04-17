package com.librarymanagementsystem.project.controller;


import com.librarymanagementsystem.project.Dtos.BorrowDto;
import com.librarymanagementsystem.project.Dtos.UserDto;
import com.librarymanagementsystem.project.entity.BorrowEntity;
import com.librarymanagementsystem.project.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/userCreation")
    public String userCreation(@RequestBody UserDto userDto){
        return userService.userCreation(userDto);
    }

    @GetMapping("/getUserDetails/{id}")
    public UserDto userDto (@PathVariable Long id){
        return userService.userDto(id);
    }

    @GetMapping("{id}/borrowed")
    public List<BorrowEntity> getBorrowedDetails(@PathVariable Long id){
        return userService.getBorrowedDetails(id);
    }

}
