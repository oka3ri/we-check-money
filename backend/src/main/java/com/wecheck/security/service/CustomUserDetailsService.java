package com.wecheck.security.service;

import com.wecheck.common.exception.CustomException;
import com.wecheck.security.dto.UserDetailsDto;
import com.wecheck.app.user.dto.UserDto;
import com.wecheck.security.mapper.AuthMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AuthMapper authMapper;
    public CustomUserDetailsService(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String nickname) throws UsernameNotFoundException{
        UserDto user = authMapper.findUserInfoByNickname(nickname);
        if(user == null) {
            throw new CustomException("해당 사용자 정보가 없습니다.");
        }
        return UserDetailsDto.build(user);
    }
}