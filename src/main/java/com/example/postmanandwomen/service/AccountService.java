package com.example.postmanandwomen.service;

import com.example.postmanandwomen.dto.AccountRequestDto;
import com.example.postmanandwomen.dto.LoginRequestDto;
import com.example.postmanandwomen.dto.ResponseDto;
import com.example.postmanandwomen.entity.Account;
import com.example.postmanandwomen.entity.RefreshToken;
import com.example.postmanandwomen.exception.RequestException;
import com.example.postmanandwomen.jwt.dto.TokenDto;
import com.example.postmanandwomen.jwt.util.JwtUtil;
import com.example.postmanandwomen.repository.AccountRepository;
import com.example.postmanandwomen.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public ResponseDto<?> signup(AccountRequestDto accountReqDto) {

        // email 중복 검사
        if(accountRepository.findByEmail(accountReqDto.getEmail()).isPresent()){
            throw new RequestException(HttpStatus.BAD_REQUEST, "Overlap Check"); // ex) return ResponseDto.fail()
        }
        System.out.println("AccountService.signup");

        accountReqDto.setEncodePwd(passwordEncoder.encode(accountReqDto.getPassword()));
        Account account = new Account(accountReqDto);

        accountRepository.save(account);
        return ResponseDto.success("Success signup");
    }

    @Transactional
    public ResponseDto<?> login(LoginRequestDto loginReqDto, HttpServletResponse response) {

        Account account = accountRepository.findByEmail(loginReqDto.getEmail()).orElseThrow(
                () -> new RequestException(HttpStatus.BAD_REQUEST, "Not found Account")
        );

        if(!passwordEncoder.matches(loginReqDto.getPassword(), account.getPassword())) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "Not matches Password");
        }

        TokenDto tokenDto = jwtUtil.createAllToken(loginReqDto.getEmail());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByAccountEmail(loginReqDto.getEmail());

        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        }else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), loginReqDto.getEmail());
            refreshTokenRepository.save(newToken);
        }

        setHeader(response, tokenDto);

        return ResponseDto.success("Success Login");

    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }
}
