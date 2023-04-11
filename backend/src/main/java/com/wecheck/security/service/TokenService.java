package com.wecheck.security.service;

import com.wecheck.security.dto.RefreshTokenDto;
import com.wecheck.security.mapper.TokenMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class TokenService {
    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
    private final TokenMapper tokenMapper;

    public Integer insertTokenInBlackList(Map<String, String> params) throws Exception {
        return tokenMapper.insertTokenInBlackList(params);
    }
    public Integer isBlackListAccessToken(String token) throws Exception {
        return tokenMapper.isBlackListAccessToken(token);
    }

    RefreshTokenDto getUserRefreshToken(Long userId) throws Exception {
        return tokenMapper.getUserRefreshToken(userId);
    }
    public Integer insertUserRefreshToken(RefreshTokenDto params) throws Exception {
        return tokenMapper.insertUserRefreshToken(params);
    }
    public Integer deleteUserRefreshToken(RefreshTokenDto params) throws Exception {
        return tokenMapper.deleteUserRefreshToken(params);
    }
}

