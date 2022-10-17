package com.example.postmanandwomen.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {
    @Lob
    private String content;
}