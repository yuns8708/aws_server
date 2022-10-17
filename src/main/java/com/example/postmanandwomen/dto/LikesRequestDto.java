package com.example.postmanandwomen.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikesRequestDto {
    private String email;

    private Long postId;
}
