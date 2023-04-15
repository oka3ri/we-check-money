package com.wecheck.app.user.controller;

import com.wecheck.app.user.dto.UserDto;
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

    @GetMapping("/user/property")
    public AppResponse checkDuplicateProperty(
            @RequestParam(value="nickname",required=false) String nickname,
            @RequestParam(value="loginId",required=false) String loginId) throws Exception {
        UserDto result = new UserDto();
        if(nickname != null) {
            result = userService.checkDuplicateNickname(nickname);
        } else if(loginId != null) {
            result = userService.checkDuplicateLoginId(loginId);
        }
        return AppResponse.builder().data(result).build();
    }

    // NOTE: token test API
    @GetMapping("/test/jwttoken")
    public String testJwtToken() {
        return "testJwt";
    }
}