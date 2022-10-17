package com.example.postmanandwomen.service;// MypageService

import com.example.postmanandwomen.dto.*;
import com.example.postmanandwomen.entity.*;
import com.example.postmanandwomen.jwt.dto.TokenDto;
import com.example.postmanandwomen.jwt.util.JwtUtil;
import com.example.postmanandwomen.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MypageService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AccountRepository accountRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    // 마이페이지 조회
    @Transactional(readOnly=true)
    public ResponseDto<?> getMypage(Account account){
        List<Post> postList = postRepository.findAllByAccount(account);
        List<Comment> commentList = commentRepository.findAllByAccount(account);
        List<Likes> likesList = likesRepository.findAllByAccount(account);
        return ResponseDto.success(
                MypageResponseDto.builder()
                        .id(account.getId())
                        .username(account.getUsername())
                        .email(account.getEmail())
                        .posts(postList.stream().map(PostResponseDto::new).collect(Collectors.toList()))
                        .comments(commentList.stream().map(CommentResponseDto::new).collect(Collectors.toList()))
                        .likes(likesList.stream().map(LikesResponseDto::new).collect(Collectors.toList()))
                        .build()
        );
    }

    @Transactional
    public ResponseDto<?> updateMypage(AccountRequestDto accountRequestDto, Account account, HttpServletResponse response){
        if(!account.getEmail().equals(accountRequestDto.getEmail())){
            TokenDto tokenDto = jwtUtil.createAllToken(accountRequestDto.getEmail());
            RefreshToken refreshToken = new RefreshToken(tokenDto.getRefreshToken(), accountRequestDto.getEmail());
            refreshTokenRepository.save(refreshToken);
            response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
            response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
        }
        accountRequestDto.setEncodePwd(passwordEncoder.encode(accountRequestDto.getPassword()));
        account.updateAccount(accountRequestDto);
        accountRepository.save(account);
        return ResponseDto.success("수정 완료"); //AccountResponseDto로 바뀐 정보 보여주기
    }


    // 회원 탈퇴

    @Transactional
    public ResponseDto<?> deleteMypage(Account account) {
        // post 삭제와 같은 함수 사용으로 대체
        List<Post> postList = postRepository.findAllByAccount(account);
        for (Post post : postList) {
            commentRepository.deleteAll(commentRepository.findAllByPost(post));
            likesRepository.deleteAll(likesRepository.findAllByPost(post));
        }
        //
        likesRepository.deleteAll(likesRepository.findAllByAccount(account));
        commentRepository.deleteAll(commentRepository.findAllByAccount(account));
        postRepository.deleteAll(postRepository.findAllByAccount(account));
        accountRepository.delete(account);
        return ResponseDto.success("회원 탈퇴 성공");
    }
}