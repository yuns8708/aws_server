package com.example.postmanandwomen.controller;// MypageController

import com.example.postmanandwomen.dto.AccountRequestDto;
import com.example.postmanandwomen.dto.ResponseDto;
import com.example.postmanandwomen.security.user.UserDetailsImpl;
import com.example.postmanandwomen.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/mypage")
public class MypageController {

    private final MypageService mypageService;

    // 내 게시글, 댓글, 좋아요 보기

    @GetMapping("")
    public ResponseDto<?> getMp(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return mypageService.getMypage(userDetails.getAccount());
    }

    // 회원정보 수정
    @PatchMapping("")
    public ResponseDto<?> updateMp(@Valid @RequestBody AccountRequestDto accountRequestDto,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails,
                                   HttpServletResponse response){
        return mypageService.updateMypage(accountRequestDto, userDetails.getAccount(),response);
    }

    // 회원 탈퇴
    @DeleteMapping("")
    public ResponseDto<?> deleteMp(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return mypageService.deleteMypage(userDetails.getAccount());
    }

}