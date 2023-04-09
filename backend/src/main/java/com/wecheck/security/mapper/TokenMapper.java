package com.wecheck.security.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("tokenMapper")
public interface TokenMapper {
    Integer insertTokenInBlackList(@Param("token") String token) throws Exception;
    Integer isBlackListAccessToken(@Param("token") String token) throws Exception;
}
