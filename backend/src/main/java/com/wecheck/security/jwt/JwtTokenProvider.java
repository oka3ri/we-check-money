package com.wecheck.security.jwt;

import com.wecheck.app.user.dto.UserDto;
import com.wecheck.common.properties.CommonProperties;
import com.wecheck.security.code.TokenErrCode;
import com.wecheck.security.service.TokenService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final CommonProperties properties;
    private final TokenService tokenService;


    public String createAccessToken(String subjectId, String nickname, Long userId) throws Exception {
        // subject > 일반가입: loginId, 소셜가입: external_id
        Claims claims = Jwts.claims().setSubject(subjectId);
        // nickname 저장
        claims.put("nickname", nickname);
        // userId 저장
        claims.put("userId", userId);

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .signWith(SignatureAlgorithm.HS256, properties.getToken().getSecret())
                .setExpiration(new Date(now.getTime()+ properties.getToken().getAccessTokenExpireTime()))
                .compact();
    }

    public String createRefreshToken(String subjectId, String nickname, Long userId) throws Exception {
        // subject > 일반가입: loginId, 소셜가입: external_id
        Claims claims = Jwts.claims().setSubject(subjectId);
        // nickname 저장
        claims.put("nickname", nickname);
        // userId 저장
        claims.put("userId", userId);

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, properties.getToken().getSecret())
                .setExpiration(new Date(now.getTime() + properties.getToken().getRefreshTokenExpireTime()))
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(properties.getToken().getSecret())
                .parseClaimsJws(token)
                .getBody();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        User principal = new User(claims.getSubject(), "", authorities);
        logger.info("claims >> {}", principal + ", token= " + token + ",authorities= " + authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // NOTE: 일반가입유저>login_id, 소셜가입유저: external_id
    public String getTokenSubject(String token) throws Exception {
        Claims claims = Jwts
                .parser()
                .setSigningKey(properties.getToken().getSecret())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Date getTokenExpireDate(String token) throws Exception {
        return Jwts
                .parser()
                .setSigningKey(properties.getToken().getSecret())
                .parseClaimsJws(token)
                .getBody()
                .get("exp", Date.class);
    }

    public Long getTokenUserId(String token) throws Exception {
        return Jwts
                .parser()
                .setSigningKey(properties.getToken().getSecret())
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class);
    }

    public boolean validateToken(String token, String type) throws Exception {
        logger.info("validateToken Method >> {} ", token);
        try {
            Jwts.parser().setSigningKey(properties.getToken().getSecret()).parseClaimsJws(token);
            // black list에 저장된 access token 여부 확인
            int isBlackListToken = 0;
            if(type.equals("atk")) {
                isBlackListToken = tokenService.isBlackListAccessToken(token);
            }
            return isBlackListToken == 0;
        } catch (MalformedJwtException e) {
            logger.info("잘못된 Token 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 Token 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 Token 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("Token 토큰이 잘못되었습니다.");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validateTokenWithException(String token, HttpServletRequest request) {
        logger.info("validateToken Method >> {} ", token);
        try {
            Jwts.parser().setSigningKey(properties.getToken().getSecret()).parseClaimsJws(token);
            // black list에 저장된 access token 여부 확인
            int isBlackListToken = tokenService.isBlackListAccessToken(token);;
            return isBlackListToken == 0;
        } catch (MalformedJwtException e) {
            request.setAttribute("exception", TokenErrCode.EMPTY_TOKEN.getCode());
            logger.info("잘못된 Token 서명입니다.");
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", TokenErrCode.TOKEN_001.getCode());
            logger.info("만료된 Token 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", TokenErrCode.EMPTY_TOKEN.getCode());
            logger.info("지원되지 않는 Token 토큰입니다.");
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", TokenErrCode.EMPTY_TOKEN.getCode());
            logger.info("Token 토큰이 잘못되었습니다.");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String resolveToken(HttpServletRequest request) {
        // request header에서 token정보 추출
        String token = request.getHeader(properties.getToken().getTokenName());
        if (StringUtils.hasText(token) && token.startsWith(properties.getToken().getPrefix()+" ")) {
            return token.substring(7);
        }
        return null;
    }

}
