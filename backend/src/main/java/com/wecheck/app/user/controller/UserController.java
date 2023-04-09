package com.wecheck.app.user.controller;

import com.wecheck.app.common.code.CommonResponse;
import com.wecheck.app.user.dto.UserDto;
import com.wecheck.security.dto.LoginDto;
import com.wecheck.security.dto.TokenDto;
import com.wecheck.security.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RequestMapping("/api")
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/test/jwttoken")
    public String testJwtToken() {
        return "testJwt";
    }
}