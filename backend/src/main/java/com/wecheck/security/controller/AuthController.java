package com.wecheck.security.controller;

import com.wecheck.app.common.code.CommonResponse;
import com.wecheck.security.dto.LoginDto;
import com.wecheck.security.dto.TokenDto;
import com.wecheck.app.user.dto.UserDto;
import com.wecheck.security.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RequestMapping("/auth")
@RestController
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(AuthService authService, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.authService = authService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/login/general")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) throws Exception{
        return authService.login(loginDto);

    }

    @PostMapping("/signup")
    public ResponseEntity<TokenDto> signup(@RequestBody UserDto params) throws Exception {
        return authService.insertSignUpUser(params);
    }

    @PostMapping("/logout")
    public CommonResponse<?> logout(HttpServletRequest request) throws Exception {
        return  authService.logout(request);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(HttpServletRequest request) throws Exception {
        return  authService.reissue(request);
    }
}