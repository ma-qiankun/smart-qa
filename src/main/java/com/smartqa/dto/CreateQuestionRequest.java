package com.smartqa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuestionRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Long userId;
}
