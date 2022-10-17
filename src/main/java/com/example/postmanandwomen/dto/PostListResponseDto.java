package com.example.postmanandwomen.dto;

import com.example.postmanandwomen.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostListResponseDto {
    // 제목
    private String title;

    // 작성자명
    private String username;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private Long likeNum;

    private Long commentNum;

    public PostListResponseDto(Post post, Long likeNum, Long commentNum) {
        this.username = post.getAccount().getUsername();
        this.title = post.getTitle();
        this.createdAt = post.getModifiedAt();
        this.modifiedAt = post.getCreatedAt();
        this.likeNum = likeNum;
        this.commentNum = commentNum;
    }
}
