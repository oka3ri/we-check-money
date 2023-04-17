package com.wecheck.app.user.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@RequestMapping("/")
@Controller
public class SampleController {

    private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
    // NOTE: token test API
    @GetMapping("/test")
    public String testJwtToken() {
        return "index";
    }
}