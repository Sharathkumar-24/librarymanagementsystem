package com.librarymanagementsystem.project.serviceimpl;

import com.librarymanagementsystem.project.Dtos.BorrowDto;
import com.librarymanagementsystem.project.Dtos.UserDto;
import com.librarymanagementsystem.project.entity.BorrowEntity;
import com.librarymanagementsystem.project.entity.UserEntity;
import com.librarymanagementsystem.project.repository.BorrowRepository;
import com.librarymanagementsystem.project.repository.UserRepository;
import com.librarymanagementsystem.project.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl  implements UserService {

    @Autowired
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private BorrowRepository borrowRepository;

    @Override
    public String userCreation(UserDto userDto) {

        if (userDto!=null && userDto.getEmail()!=null &&  !userDto.getEmail().trim().isEmpty() &&
                userDto.getName()!=null && !userDto.getName().trim().isEmpty() &&
                userDto.getUserrole()!=null) {
                userDto.setMaxBooksAllowed(5);
            UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
            userRepository.save(userEntity);
            return "User Added";
        }
         else return "Invalid Data";
    }

    @Override
    public UserDto userDto(Long id) {
        Optional<UserEntity> details = userRepository.findById(id);
        UserDto  userDetails = modelMapper.map(details.get(),UserDto.class);
        return userDetails;
    }

    @Override
    public List<BorrowEntity> getBorrowedDetails(Long id) {
        Optional<UserEntity> userDetails = userRepository.findById(id);
        String username =userDetails.get().getName();
        return borrowRepository.findByUserName(username);
    }


}
