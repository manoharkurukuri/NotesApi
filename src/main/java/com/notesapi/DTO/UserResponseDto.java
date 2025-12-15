package com.notesapi.DTO;



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
public class UserResponseDto {


    private String id;
    private String username;
    private String fullName;
    private String email;
    private String imageUrl;
    private String token;
    private Instant createdAt;
    private LocalDateTime updatedAt;

}
