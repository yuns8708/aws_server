package com.example.postmanandwomen.dto;

import com.example.postmanandwomen.entity.Account;
import com.example.postmanandwomen.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class PostResponseDto {
    private String title;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private String username;

    public PostResponseDto(Post post) {
        this.username = post.getAccount().getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
