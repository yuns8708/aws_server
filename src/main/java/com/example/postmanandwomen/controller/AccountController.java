package com.example.postmanandwomen.controller;

import com.example.postmanandwomen.dto.AccountRequestDto;
import com.example.postmanandwomen.dto.LoginRequestDto;
import com.example.postmanandwomen.dto.ResponseDto;
import com.example.postmanandwomen.jwt.util.JwtUtil;
import com.example.postmanandwomen.security.user.UserDetailsImpl;
import com.example.postmanandwomen.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final JwtUtil jwtUtil;
    private final AccountService accountService;

    @PostMapping("/signup")
    public ResponseDto<?> signup(@RequestBody @Valid AccountRequestDto accountRequestDto) {
        System.out.println("AccountController.signup");
        return accountService.signup(accountRequestDto);
    }

    @PostMapping("/login")
    public ResponseDto<?> login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return accountService.login(loginRequestDto, response);
    }

    @GetMapping("/issue/token")
    public ResponseDto<?> issuedToken(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response){
        response.addHeader(JwtUtil.ACCESS_TOKEN, jwtUtil.createToken(userDetails.getAccount().getEmail(), "Access"));
        return ResponseDto.success("Success IssuedToken");
    }

}