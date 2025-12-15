package com.notesapi.Document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "notes")
public class Notes {

    private String id;
    private String title;
    private String content;
    private String ownerId;
    private List<String> tags;
    private String color;
    private boolean isPinned;
    private boolean isArchived;
    private boolean isTrashed;
    private boolean isPublic;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;



}
