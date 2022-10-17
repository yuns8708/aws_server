package com.example.postmanandwomen.service;

import com.example.postmanandwomen.dto.*;
import com.example.postmanandwomen.entity.Account;
import com.example.postmanandwomen.entity.Comment;
import com.example.postmanandwomen.entity.Likes;
import com.example.postmanandwomen.entity.Post;
import com.example.postmanandwomen.exception.RequestException;
import com.example.postmanandwomen.repository.CommentRepository;
import com.example.postmanandwomen.repository.LikesRepository;
import com.example.postmanandwomen.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final LikesRepository likesRepository;
    private final CommentRepository commentRepository;

    // 글 생성 (user 매핑)
    public ResponseDto createPost(Account account, PostRequestDto requestDto) {
        Post post = new Post(account, requestDto);
        postRepository.save(post);
        PostResponseDto responseDto = new PostResponseDto(post);
        return ResponseDto.success(responseDto);
    }

    // 글 목록 가져오기
    public ResponseDto findAllPosts() {
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();
        List<PostListResponseDto> postList = new ArrayList<>();
        for (Post post: posts) {
            Long likeNum = (long) likesRepository.findAllByPost(post).size();
            Long commentNum = (long) commentRepository.findAllByPost(post).size();
            postList.add(new PostListResponseDto(post, likeNum, commentNum));
        }
        return ResponseDto.success(postList);
    }

    // 글 하나 가져오기
    public ResponseDto findOnePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new RequestException(HttpStatus.NOT_FOUND,"해당 게시글이 존재하지 않습니다.")
        );
        Long likeNum = Long.valueOf(likesRepository.findAllByPost(post).size());
        List<Comment> commentList = commentRepository.findAllByPost(post);
        List<CommentResponseDto> responseDtoList = commentList.stream().map(CommentResponseDto::new).collect(Collectors.toList());

        PostOneResponseDto response = new PostOneResponseDto(post, likeNum, responseDtoList);
        return ResponseDto.success(response);
    }

    // 글 수정
    @Transactional
    public ResponseDto updatePost(Account account, Long id, PostRequestDto requestDto) {
        // 어떤 게시판인지 찾기
        Post post = postRepository.findById(id).orElseThrow(
                () -> new RequestException(HttpStatus.NOT_FOUND,"해당 게시글이 존재하지 않습니다.")
        );

        // 게시판의 유저와 로그인 유저 비교
        if (account.getEmail().equals(post.getAccount().getEmail())) {
            post.update(requestDto);
            return ResponseDto.success(new PostResponseDto(post));
        } else {
            throw new RequestException(HttpStatus.FORBIDDEN,"작성자만 수정할 수 있습니다");
        }
    }

    // 삭제
    @Transactional
    public ResponseDto deletePost(Account account, Long id) {
        // 어떤 게시판인지 찾기
        Post post = postRepository.findById(id).orElseThrow(
                () -> new RequestException(HttpStatus.NOT_FOUND,"해당 게시글이 존재하지 않습니다.")
        );

        // 게시판의 email과 로그인 유저의 email 비교
        if (account.getEmail().equals(post.getAccount().getEmail())) {
            // 게시판의 댓글도 삭제
            List<Comment> comment = commentRepository.findAllByPost(post);
            commentRepository.deleteAll(comment);
            List<Likes> likes = likesRepository.findAllByPost(post);
            likesRepository.deleteAll(likes);
            postRepository.deleteById(id);
            return ResponseDto.success("삭제되었습니다");
        } else {
            throw new RequestException(HttpStatus.FORBIDDEN,"작성자만 삭제할 수 있습니다");
        }
    }
}
