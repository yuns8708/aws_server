package com.example.postmanandwomen.controller;

import com.example.postmanandwomen.dto.LikesRequestDto;
import com.example.postmanandwomen.dto.ResponseDto;
import com.example.postmanandwomen.security.user.UserDetailsImpl;
import com.example.postmanandwomen.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LikesController {
    private final LikesService likesService;

    // 좋아요 기능
    @PostMapping("/auth/post/likes/{postId}")
    public ResponseDto<?> like(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId) {
        return likesService.like(userDetails.getAccount(), postId);
    }
}
