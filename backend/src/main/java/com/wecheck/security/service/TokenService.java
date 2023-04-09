package com.wecheck.security.service;

import com.wecheck.security.mapper.TokenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
    private final TokenMapper tokenMapper;

    public TokenService(TokenMapper tokenMapper) {
        this.tokenMapper = tokenMapper;
    }

    public Integer insertTokenInBlackList(String token) throws Exception {
        return tokenMapper.insertTokenInBlackList(token);
    }
    public Integer isBlackListAccessToken(String token) throws Exception {
        return tokenMapper.isBlackListAccessToken(token);
    }
}

