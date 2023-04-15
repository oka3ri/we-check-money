package com.wecheck.security.controller;

import com.wecheck.app.user.dto.UserDto;
import com.wecheck.common.response.CommonResponse;
import com.wecheck.security.dto.LoginDto;
import com.wecheck.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;


    @PostMapping("/app")
    public CommonResponse login(@RequestBody LoginDto loginDto) throws Exception{
        return authService.login(loginDto);

    }

    @PostMapping("/app/new")
    public CommonResponse signup(@RequestBody UserDto params) throws Exception {
        return authService.insertSignUpUser(params);
    }

    @DeleteMapping("/app")
    public CommonResponse logout(HttpServletRequest request) throws Exception {
        return  authService.logout(request);
    }

    @PutMapping("/token")
    public CommonResponse reissueAccessToken(HttpServletRequest request) throws Exception {
        return  authService.reissueAccessToken(request);
    }
}