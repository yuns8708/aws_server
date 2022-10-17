// Account.class
package com.example.postmanandwomen.entity;

import com.example.postmanandwomen.dto.AccountRequestDto;
import com.example.postmanandwomen.dto.TimeStamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String username;

    private String password;

    public Account(AccountRequestDto accountRequestDto) {
        this.email = accountRequestDto.getEmail();
        this.password = accountRequestDto.getPassword();
        this.username = accountRequestDto.getUsername();
    }
    // 회원 정보 수정
    public void updateAccount(AccountRequestDto accountRequestDto){
        this.email = accountRequestDto.getEmail();
        this.password = accountRequestDto.getPassword();
        this.username = accountRequestDto.getUsername();
    }
}