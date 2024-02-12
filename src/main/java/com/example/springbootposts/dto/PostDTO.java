package com.example.springbootposts.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
    private Long authorId;
    private Set<Long> categoryIds;
}
