// LoginRequestDto.java
package com.example.postmanandwomen.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "Email은 공백일 수 없습니다.")
    private String email;

    @NotBlank(message = "Password는 공백일 수 없습니다.")
    private String password;

}