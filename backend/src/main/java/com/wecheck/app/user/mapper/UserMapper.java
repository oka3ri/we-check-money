package com.wecheck.app.user.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("userMapper")
public interface UserMapper {
    public Integer checkDuplicateNickname(@Param("nickname") String nickname) throws Exception;
    public Integer checkDuplicateLoginId(@Param("loginId") String loginId) throws Exception;
}
