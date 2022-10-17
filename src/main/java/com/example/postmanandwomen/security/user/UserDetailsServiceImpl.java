package com.example.postmanandwomen.security.user;

import com.example.postmanandwomen.entity.Account;
import com.example.postmanandwomen.exception.RequestException;
import com.example.postmanandwomen.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Account account = accountRepository.findByEmail(email).orElseThrow(
                () -> new RequestException(HttpStatus.UNAUTHORIZED, "Account를 찾을 수 없습니다.")
        );

        UserDetailsImpl userDetails = new UserDetailsImpl(account);
//        userDetails.setAccount(account);

        return userDetails;
    }
}
