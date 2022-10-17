package com.example.postmanandwomen.dto;

import com.example.postmanandwomen.entity.Likes;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LikesResponseDto {
    private Long postId;

    private String postTitle;

    public LikesResponseDto(Likes likes) {
        this.postId = likes.getPost().getId();
        this.postTitle = likes.getPost().getTitle();
    }
}
