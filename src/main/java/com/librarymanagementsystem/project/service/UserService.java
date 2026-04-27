package com.librarymanagementsystem.project.service;


import com.librarymanagementsystem.project.Dtos.UserDto;
import com.librarymanagementsystem.project.entity.BorrowEntity;
import com.librarymanagementsystem.project.entity.UserEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    String userCreation(UserDto userDto);

    UserDto userDto(Long id);

    List<BorrowEntity> getBorrowedDetails(Long id);

    String verify(UserEntity userEntity, AuthenticationManager authenticationManager);

    UserDetails loadUserByUsername(String username);
}
