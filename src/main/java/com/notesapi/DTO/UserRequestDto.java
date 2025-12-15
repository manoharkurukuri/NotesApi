package com.notesapi.DTO;


import com.notesapi.Document.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {


    private String id;
    private String username;
    @NotBlank(message = "FullName is Required")
    private String fullName;
    @Email(message = "Email Should be valid")
    @NotBlank(message = "Email is Required")
    private String email;
    @NotBlank(message = "Password is Required")
    @Size(min = 6,message = "Password must be greater than 6")
    private String password;
    private String imageUrl;
    private Instant createdAt;
    private LocalDateTime updatedAt;


}
