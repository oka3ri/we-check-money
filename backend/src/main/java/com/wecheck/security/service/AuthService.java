package com.wecheck.security.service;

import com.wecheck.app.user.dto.UserDto;
import com.wecheck.app.user.service.UserService;
import com.wecheck.common.exception.CustomException;
import com.wecheck.common.properties.CommonProperties;
import com.wecheck.common.response.CommonResponse;
import com.wecheck.security.code.TokenErrCode;
import com.wecheck.security.dto.LoginDto;
import com.wecheck.security.dto.RefreshTokenDto;
import com.wecheck.security.dto.TokenDto;
import com.wecheck.security.jwt.JwtTokenProvider;
import com.wecheck.security.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final AuthMapper authMapper;
    private final TokenService tokenService;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CommonProperties properties;


    @Transactional
    public CommonResponse login(LoginDto loginDto) throws Exception {
        UserDto user = authMapper.findUserInfoByLoginId(loginDto.getLoginId());
        if(user == null) {
            throw new CustomException("해당 사용자 정보가 존재하지 않습니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);
        // DELETE: 기존 유저 리프레시토큰 삭제
        tokenService.deleteUserRefreshToken(RefreshTokenDto.builder().userId(user.getUserId()).build());
        // INSERT: 새로운 리프레시토큰 저장
        long tokenExp = jwtTokenProvider.getTokenExpireDate(refreshToken).getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateFmt = sdf.format(new Date(tokenExp));
        tokenService.insertUserRefreshToken(RefreshTokenDto.builder().userId(user.getUserId()).refreshToken(refreshToken).expiredDate(dateFmt).build());

        return CommonResponse.of(true, TokenDto.builder().accessToken(accessToken).refreshToken(refreshToken).build());
    }

    @Transactional
    public CommonResponse logout(HttpServletRequest servletRequest) throws Exception {
        String accessToken = jwtTokenProvider.resolveToken(servletRequest);
        // 토큰 유효시간
        long tokenExp = jwtTokenProvider.getTokenExpireDate(accessToken).getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateFmt = sdf.format(new Date(tokenExp));
        // INSERT: access token 블랙리스트 저장
        Map<String, String> bParams = new HashMap<>();
        bParams.put("accessToken", accessToken);
        bParams.put("expiredDate", dateFmt);
        tokenService.insertTokenInBlackList(bParams);
        // DELETE: refresh token 정보 삭제
        Long userId = jwtTokenProvider.getUserId(accessToken);
        tokenService.deleteUserRefreshToken(RefreshTokenDto.builder().userId(userId).build());

        return CommonResponse.of(true, Optional.empty());
    }

    // NOTE: access 토큰 만료시 재발급 요청
    public CommonResponse reissueAccessToken(HttpServletRequest request) throws Exception {
        String refreshToken = jwtTokenProvider.resolveToken(request);
        // NOTE: 넘겨받은 토큰값 유효성 검증
        if(!jwtTokenProvider.validateToken(refreshToken, "rtk")) {
           return CommonResponse.of(false, TokenErrCode.TOKEN_003.getCode(), TokenErrCode.TOKEN_003.getMessage());
        }

        Long tkUserId = jwtTokenProvider.getUserId(refreshToken); // 유저아이디 추출
        UserDto user = authMapper.getUserInfo(UserDto.builder().userId(tkUserId).build());
        if(user == null) {
            throw new CustomException("해당 사용자 정보가 존재하지 않습니다.");
        }

        // DB 리프레시토큰값 검증
        RefreshTokenDto rtkDto = tokenService.getUserRefreshToken(tkUserId);
        // NOTE: DB에 저장된 토큰 정보가 없을 경우 || 넘어온 토큰값과 DB 토큰값이 다를 경우 에러처리
        if(rtkDto == null || !rtkDto.getRefreshToken().equals(refreshToken)) {
            return CommonResponse.of(false, TokenErrCode.TOKEN_003.getCode(), TokenErrCode.TOKEN_003.getMessage());
        }

        return CommonResponse.of(true, TokenDto.builder().accessToken(jwtTokenProvider.createAccessToken(user)).build());

    }

    @Transactional
    public CommonResponse insertSignUpUser(UserDto params) throws Exception {
        // 닉네임 중복검사
        boolean isDuplicateNickname = userService.checkDuplicateNickname(params.getNickname()) != null;
        if(isDuplicateNickname) {
            throw new CustomException("이미 등록된 닉네임입니다.");
        }
        // 로그인 아이디 중복검사
        boolean isDuplicateLoginId = userService.checkDuplicateLoginId(params.getLoginId()) != null;
        if(isDuplicateLoginId) {
            throw new CustomException("이미 등록된 아이디입니다.");
        }

        int resultInsert = authMapper.insertSignUpUser(params);
        UserDto result = new UserDto();
        if(resultInsert > 0) {
            // NOTE: 비밀번호 암호화(Spring Security 암호화방식)
            params.setPassword(passwordEncoder.encode(params.getPassword()));
            // INSERT: 일반회원 정보
            authMapper.insertSignUpUserAuthInfo(params);
            result = authMapper.getUserInfo(params);
        }

        String accessToken = jwtTokenProvider.createAccessToken(result);
        String refreshToken = jwtTokenProvider.createRefreshToken(result);
        // INSERT: user refreshToken
        long tokenExp = jwtTokenProvider.getTokenExpireDate(refreshToken).getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateFmt = sdf.format(new Date(tokenExp));
        tokenService.insertUserRefreshToken(RefreshTokenDto.builder()
                .refreshToken(refreshToken)
                .userId(result.getUserId())
                .expiredDate(dateFmt).build());

        return CommonResponse.of(true, TokenDto.builder().accessToken(accessToken).refreshToken(refreshToken).build());
    }
}
