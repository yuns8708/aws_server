package com.example.postmanandwomen.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class AccountUpdateDto {
    private String email;
    private String username;
    private String password;
    private String passwordConfirm;
}
