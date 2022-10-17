package com.example.postmanandwomen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MypageResponseDto {
    private Long id;
    private String username;
    private String email;
    private List<PostResponseDto> posts;
    private List<CommentResponseDto> comments;
    private List<LikesResponseDto> likes;
}
