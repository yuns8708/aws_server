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
public class PostOneResponseDto {
    // 제목
    private String title;

    // 작성자명
    private String username;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private Long likeNum;

    private List<CommentResponseDto> comments;

    public PostOneResponseDto(Post post, Long likeNum, List<CommentResponseDto> commentList) {
        this.username = post.getAccount().getUsername();
        this.title = post.getTitle();
        this.createdAt = post.getModifiedAt();
        this.modifiedAt = post.getCreatedAt();
        this.likeNum = likeNum;
        this.comments = commentList;
    }
}
