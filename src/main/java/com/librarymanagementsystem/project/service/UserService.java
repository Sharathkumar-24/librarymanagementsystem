package com.librarymanagementsystem.project.service;


import com.librarymanagementsystem.project.Dtos.BorrowDto;
import com.librarymanagementsystem.project.Dtos.UserDto;
import com.librarymanagementsystem.project.entity.BorrowEntity;

import java.util.List;

public interface UserService {

    String userCreation(UserDto userDto);

    UserDto userDto(Long id);

    List<BorrowEntity> getBorrowedDetails(Long id);
}
