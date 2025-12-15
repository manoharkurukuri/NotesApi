package com.notesapi.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateNoteRequestDto {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    private boolean isPinned;


    private List<String> tags;
    private String color;

}
