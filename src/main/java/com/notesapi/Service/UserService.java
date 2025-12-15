package com.notesapi.Service;


import com.notesapi.DTO.UserRequestDto;
import com.notesapi.DTO.UserResponseDto;
import com.notesapi.Document.User;
import com.notesapi.Repositories.UserRepository;
import com.notesapi.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    public void createUser(@Valid UserRequestDto userRequestDto) {

       boolean userExist =  userRepository.findByEmail(userRequestDto.getEmail()).isPresent();
         if(userExist) {
             throw new RuntimeException("User with email " + userRequestDto.getEmail() + " already exist");
         }


        userRepository.save(toDocument(userRequestDto));
    }

    private User toDocument(UserRequestDto userRequestDto) {

        return User.builder()
                .fullName(userRequestDto.getFullName())
                .email(userRequestDto.getEmail())
                .username(UUID.randomUUID().toString().replaceAll("[^0-9]","").substring(0,10))
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .imageUrl("https://robohash.org/" + userRequestDto.getFullName().replaceAll(" ",""))
                .createdAt(Instant.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public UserResponseDto loginUser(User loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()-> new RuntimeException("User with email " + loginRequest.getEmail() + " not found"));

        boolean isPasswordMatch = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        if(!isPasswordMatch) {
            throw new RuntimeException("Invalid password");
        }
        String token = jwtUtil.generateToken(user.getId());

        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .imageUrl(user.getImageUrl())
                .token(token)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();


    }

    public User updateProfile(String id, User userRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));

        user.setFullName(userRequestDto.getFullName());
        user.setEmail(userRequestDto.getEmail());
        user.setImageUrl(userRequestDto.getImageUrl());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }


}
