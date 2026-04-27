package com.librarymanagementsystem.project.serviceimpl;

import com.librarymanagementsystem.project.Dtos.UserDto;
import com.librarymanagementsystem.project.entity.BorrowEntity;
import com.librarymanagementsystem.project.entity.UserEntity;
import com.librarymanagementsystem.project.repository.BorrowRepository;
import com.librarymanagementsystem.project.repository.UserRepository;
import com.librarymanagementsystem.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final BorrowRepository borrowRepository;
    private final JWTService jwtService;
    private final PasswordEncoder encoder;

    @Override
    public String userCreation(UserDto userDto) {
        if (userDto == null ||
                userDto.getEmail() == null || userDto.getEmail().trim().isEmpty() ||
                userDto.getName() == null || userDto.getName().trim().isEmpty() ||
                userDto.getUserrole() == null ||
                userDto.getPassword() == null || userDto.getPassword().trim().isEmpty()) {
            return "Invalid Data";
        }

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setMaxBooksAllowed(5);
        userEntity.setPassword(encoder.encode(userEntity.getPassword()));

        userRepository.save(userEntity);
        return "User Added Successfully";
    }

    @Override
    public UserDto userDto(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<BorrowEntity> getBorrowedDetails(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return borrowRepository.findByUserName(user.getName());
    }

    @Override
    public String verify(UserEntity userEntity, AuthenticationManager authenticationManager) {
        try {
            System.out.println("Verify called: " + userEntity.getEmail());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userEntity.getEmail(),
                            userEntity.getPassword()
                    )
            );

            System.out.println("Authenticated = " + authentication.isAuthenticated());

            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(userEntity.getEmail());
                System.out.println("Token = " + token);
                return token;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "fail";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
