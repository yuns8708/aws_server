//package com.example.postmanandwomen.entity;
//
//import com.example.postmanandwomen.dto.PostRequestDto;
//import com.example.postmanandwomen.repository.AccountRepository;
//import com.example.postmanandwomen.repository.PostRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class PostTest {
//    @Autowired // 의존관계 자동 주입
//    PostRepository postRepository;
//    @Autowired
//    AccountRepository accountRepository;
//
//
//    @Test // 테스트용 메소드임을 표현함
//    @DisplayName("정상 케이스")
//    public void createPost_Normal() {
//        Account account = new Account(1L, "asdf@gmail.com", "user1", "asdf1234!");
//
//        // given
//        Long id = 1L;
//
//        String title = "테스트 제목";
//
//        String content = "테스트 내용";
//
//        PostRequestDto requestDto = new PostRequestDto(title, content);
//
//        // when
//        Post post = new Post(account, requestDto);
//
//        // then
//
//        System.out.println(account.getId());
//        // assertNull(a) : a가 null인지 확인
//        assertNull(post.getId());
//        // assertEquals(a,b) : a와 b값이 동일한지 확인
////        assertEquals(id,post.getId());
//        assertEquals(requestDto.getTitle(),post.getTitle());
////        assertEquals(content,post.getContent());
//
//        // DB test
//        accountRepository.save(account);
//
////        accountRepository.findByEmail("asdf@gmail.com");
//
//    }
//
//}