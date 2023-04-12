package com.wecheck.app.user.controller;

import com.wecheck.app.user.service.UserService;
import com.wecheck.common.response.AppResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @GetMapping("/user/checkNickname")
    public AppResponse checkDuplicateNickname(@RequestParam String nickname) throws Exception {
        int result = userService.checkDuplicateNickname(nickname);
        return AppResponse.builder().totalCount(result).build();
    }

    @GetMapping("/user/checkLoginId")
    public AppResponse checkDuplicateLoginId(@RequestParam String loginId) throws Exception{
        int result = userService.checkDuplicateLoginId(loginId);
        return AppResponse.builder().totalCount(result).build();
    }

    @GetMapping("/test/jwttoken")
    public String testJwtToken() {
        return "testJwt";
    }
}