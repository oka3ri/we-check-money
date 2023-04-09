package com.wecheck.security.service;

import com.wecheck.app.common.code.CommonResponse;
import com.wecheck.common.exception.CustomException;
import com.wecheck.common.properties.CommonProperties;
import com.wecheck.security.dto.LoginDto;
import com.wecheck.security.dto.TokenDto;
import com.wecheck.app.user.dto.UserDto;
import com.wecheck.security.jwt.JwtTokenProvider;
import com.wecheck.security.mapper.TokenMapper;
import com.wecheck.security.mapper.AuthMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final AuthMapper authMapper;
    private final TokenMapper tokenMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CommonProperties properties;

    public AuthService(AuthMapper authMapper, TokenMapper tokenMapper, BCryptPasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider, CommonProperties properties) {
        this.authMapper = authMapper;
        this.tokenMapper = tokenMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.properties = properties;
    }

    public ResponseEntity<TokenDto> login(LoginDto loginDto) throws Exception {
        UserDto user = authMapper.findUserInfoByLoginId(loginDto.getLoginId());
        if(user == null) {
            throw new CustomException("해당 사용자 정보가 존재하지 않습니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getNickname());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getNickname());
        logger.info("jwt >>>> {} ", accessToken);
        user.setRefreshToken(refreshToken);
        authMapper.updateRefreshToken(user);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(properties.getToken().getAccessTokenName(), "Bearer " + accessToken);
        logger.info("httpHeaders >>> {} ", httpHeaders.toString());

        return new ResponseEntity<>(TokenDto.builder().accessToken(accessToken).refreshToken(refreshToken).build(), httpHeaders, HttpStatus.OK);

    }

    public CommonResponse<?> logout(HttpServletRequest servletRequest) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String accessToken = jwtTokenProvider.resolveToken(httpServletRequest);

        tokenMapper.insertTokenInBlackList(accessToken);

        return CommonResponse.of(true, Optional.empty());

    }

    public ResponseEntity<TokenDto> reissue(HttpServletRequest servletRequest) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        // refreshToken 추출
        String refreshToken = jwtTokenProvider.resolveRefreshToken(httpServletRequest);
        logger.info("debugging >> refreshtoken >> {}", refreshToken);
        // refreshToken user Nickname 추출
        String nickname = jwtTokenProvider.getTokenSubject(refreshToken);
        // DB의 refreshToken값과 비교
        UserDto user = authMapper.findUserInfoByNickname(nickname);
        if(user == null) {
            throw new CustomException("해당 사용자 정보가 존재하지 않습니다.");
        }
        if(user.getRefreshToken() == null) {
            logger.info("refresh-token is null");
            throw new CustomException("refresh-token is null");
        }
        // check refresh token validate
        if(!jwtTokenProvider.validateRefreshToken(user.getRefreshToken())) {
            logger.info("기간 만료된 refresh token");
            // refresh token 삭제
            authMapper.deleteRefreshToken(user);
            throw new CustomException("expire refresh-token");
        }

        String reissueAccessToken = jwtTokenProvider.createAccessToken(user.getNickname());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(properties.getToken().getAccessTokenName(), "Bearer " + reissueAccessToken);
        logger.info("httpHeaders >>> {} ", httpHeaders.toString());

        return new ResponseEntity<>(TokenDto.builder().accessToken(reissueAccessToken).build(), httpHeaders, HttpStatus.OK);

    }

    public ResponseEntity<TokenDto> insertSignUpUser(UserDto params) throws Exception {
        // NOTE: 닉네임 중복검사
        boolean isDuplicateNickname = authMapper.checkDuplicateNickname(params) > 0;
        if(isDuplicateNickname) {
            throw new CustomException("이미 등록된 닉네임입니다.");
        }

        int resultInsert = authMapper.insertSignUpUser(params);
        boolean success = resultInsert > 0;
        UserDto result = new UserDto();
        if(success) {
            // NOTE: 비밀번호 암호화(Spring Security 암호화방식)
            params.setPassword(passwordEncoder.encode(params.getPassword()));
            // NOTE: 일반회원 테이블 INSERT
            resultInsert = authMapper.insertSignUpUserAuthInfo(params);
            result = resultInsert > 0 ? authMapper.getUserInfo(params) : null;
            success = (result != null);
        }

        String accessToken = jwtTokenProvider.createAccessToken(result.getNickname());
        String refreshToken = jwtTokenProvider.createRefreshToken(result.getNickname());
        logger.info("jwt >>>> {} ", accessToken);
        result.setRefreshToken(refreshToken);
        authMapper.updateRefreshToken(result);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(properties.getToken().getAccessTokenName(), "Bearer " + accessToken);
        logger.info("httpHeaders >>> {} ", httpHeaders.toString());

        return new ResponseEntity<>(TokenDto.builder().accessToken(accessToken).build(), httpHeaders, HttpStatus.OK);

    }
}
