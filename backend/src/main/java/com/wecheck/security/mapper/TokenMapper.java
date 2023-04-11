package com.wecheck.security.mapper;

import com.wecheck.security.dto.RefreshTokenDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository("tokenMapper")
public interface TokenMapper {
    Integer insertTokenInBlackList(Map<String, String>params) throws Exception;
    Integer isBlackListAccessToken(@Param("token") String token) throws Exception;

    RefreshTokenDto getUserRefreshToken(@Param("userId") Long userId) throws Exception;
    Integer insertUserRefreshToken(RefreshTokenDto params) throws Exception;
    Integer deleteUserRefreshToken(RefreshTokenDto params) throws Exception;
}
