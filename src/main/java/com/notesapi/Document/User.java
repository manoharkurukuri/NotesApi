package com.notesapi.Document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String username;
    private String fullName;
    private String email;
    private String password;
    private String imageUrl;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
