package com.example.postmanandwomen.entity;

import com.example.postmanandwomen.dto.PostRequestDto;
import com.example.postmanandwomen.dto.TimeStamped;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Post extends TimeStamped {
    // 글 고유 아이디
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "postId")
    private Long id;

    // 글 제목
    @Column(nullable = false)
    private String title;

    // 글 내용
    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;

    @JsonIgnore
    @OneToMany(mappedBy = "post")
    List<Likes> likes = new ArrayList<>();

    public Post(Account account, PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.account = account;
    }
    // 업데이트 메소드
    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

}
