// CommentRepository
package com.example.postmanandwomen.repository;

import com.example.postmanandwomen.entity.Account;
import com.example.postmanandwomen.entity.Comment;
import com.example.postmanandwomen.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long postId);
    List<Comment> findAllByPost(Post post);
    List<Comment> findAllByAccount(Account account);
}