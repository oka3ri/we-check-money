package com.wecheck.security.controller;

import com.wecheck.app.user.dto.UserDto;
import com.wecheck.common.response.CommonResponse;
import com.wecheck.security.dto.LoginDto;
import com.wecheck.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;


    @PostMapping("/login")
    public CommonResponse login(@RequestBody LoginDto loginDto) throws Exception{
        return authService.login(loginDto);

    }

    @PostMapping("/signup")
    public CommonResponse signup(@RequestBody UserDto params) throws Exception {
        return authService.insertSignUpUser(params);
    }

    @PostMapping("/logout")
    public CommonResponse logout(HttpServletRequest request) throws Exception {
        return  authService.logout(request);
    }

    @PostMapping("/reissue")
    public CommonResponse reissue(HttpServletRequest request) throws Exception {
        return  authService.reissue(request);
    }
}