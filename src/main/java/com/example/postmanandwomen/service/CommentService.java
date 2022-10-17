// CommentService.class
package com.example.postmanandwomen.service;

import com.example.postmanandwomen.dto.CommentRequestDto;
import com.example.postmanandwomen.dto.CommentResponseDto;
import com.example.postmanandwomen.dto.ResponseDto;
import com.example.postmanandwomen.entity.Account;
import com.example.postmanandwomen.entity.Comment;
import com.example.postmanandwomen.entity.Post;
import com.example.postmanandwomen.exception.RequestException;
import com.example.postmanandwomen.repository.CommentRepository;
import com.example.postmanandwomen.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public ResponseDto registerComment(Long postId, CommentRequestDto CommentRequestDto, Account account) {
        String content = CommentRequestDto.getContent();

        /* findAllComments 에도 동일한 코드 */
        /* 리펙토링 대상 */
        Optional<Post> findPost = postRepository.findById(postId);

        if(!findPost.isPresent()) {
            return ResponseDto.fail(HttpStatus.BAD_REQUEST, "Not existed post");
        }

        Comment savedComment = commentRepository.save(new Comment(findPost.get(), content, account));
        CommentResponseDto commentResponseDto = new CommentResponseDto(savedComment);

        return ResponseDto.success(commentResponseDto);
    }

    public ResponseDto findAllComments(Long postId) {
        Optional<Post> findPost = postRepository.findById(postId);

        if(!(findPost.isPresent())) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "Not existed post");
        }

        List<Comment> findAllComment = commentRepository.findAllByPostId(postId);
        List<CommentResponseDto> comments = new ArrayList<>();

        for (Comment savedComment : findAllComment) {
            comments.add(new CommentResponseDto(savedComment));
        }
        return ResponseDto.success(comments);
    }

    @Transactional
    public ResponseDto removeComment(Long postId, Long commentId) {
        Optional<Post> findPost = postRepository.findById(postId);
        Optional<Comment> findComment = commentRepository.findById(commentId);

        if(!findPost.isPresent()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "Not existed Post");
        }
        if(!findComment.isPresent()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "Not existed Comment");
        }

        commentRepository.deleteById(commentId);
        return ResponseDto.success("삭제 완료");
    }

    @Transactional
    public ResponseDto modifyComment(Long postId, Long commentId,
                                     CommentRequestDto commentRequestDto) {
        Optional<Post> findPost = postRepository.findById(postId);
        Optional<Comment> findComment = commentRepository.findById(commentId);

        if(!findPost.isPresent()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "Not existed Post");
        }
        if(!(findComment.isPresent())) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "Not existed Comment");
        }
        Comment comment = findComment.get();
        comment.updateComment(commentRequestDto.getContent());
        commentRepository.save(comment);

        return ResponseDto.success(new CommentResponseDto(findComment.get()));
    }

}