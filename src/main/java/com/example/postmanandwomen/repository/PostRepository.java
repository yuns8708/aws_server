package com.example.postmanandwomen.repository;

import com.example.postmanandwomen.dto.PostListResponseDto;
import com.example.postmanandwomen.entity.Account;
import com.example.postmanandwomen.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByModifiedAtDesc();
    List<Post> findAllByAccount(Account account);
}
