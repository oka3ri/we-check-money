package com.wecheck.app.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api")
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/test/jwttoken")
    public String testJwtToken() {
        return "testJwt";
    }
}