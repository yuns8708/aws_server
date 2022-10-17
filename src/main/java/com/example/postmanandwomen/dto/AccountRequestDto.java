package com.example.postmanandwomen.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class AccountRequestDto {

    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}";

    @NotBlank(message = "Username은 공백일 수 없습니다.")
    private String username;

    // 에러처리 1
    // 회원가입 시 이메일 형식이 유효하지 않은 경우,
    @NotBlank(message = "Email은 공백일 수 없습니다.")
    @Email(message = "Email 형식이 잘못되었습니다")
    private String email;

    // 에러처리 2
    // 비밀번호가 영어대소문자, 숫자, 특수문자를 모두 포함하지 않은 경우 + 8 ~ 16자리    @NotBlank
    @Pattern(regexp = PASSWORD_REGEX,message = "패스워드는 무조건 영문, 숫자, 특수문자를 1글자 이상 포함해야 합니다.")
    @NotBlank(message = "Password는 공백일 수 없습니다.")
    private String password;


    public void setEncodePwd(String encodePwd) {
        this.password = encodePwd;
    }

}