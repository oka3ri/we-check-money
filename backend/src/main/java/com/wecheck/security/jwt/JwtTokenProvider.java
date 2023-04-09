package com.wecheck.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wecheck.common.properties.CommonProperties;
import com.wecheck.app.user.dto.UserDto;
import com.wecheck.security.service.TokenService;
import io.jsonwebtoken.*;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;


@Component
public class JwtTokenProvider {
    private final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private static final String AUTHORITIES_KEY = "roles";
    //public static final String AUTHORIZATION_HEADER = "Authorization";
    private CommonProperties properties;
    private final TokenService tokenService;
    //private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // NOTE: 만료시간 30분
    //private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // NOTE: 만료시간 일주일

    public JwtTokenProvider(CommonProperties properties, TokenService tokenService) {
        this.properties = properties;
        this.tokenService = tokenService;
    }

    public String createAccessToken(UserDto user) throws Exception {
        // user객체 저장
        ObjectMapper mapper = new ObjectMapper();
        String jsonUser = mapper.writeValueAsString(user);
        Claims claims = Jwts.claims().setSubject(jsonUser); // JWT payload 에 저장되는 정보단위
        claims.put("roles", "ROLE_USER");

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .signWith(SignatureAlgorithm.HS256, properties.getToken().getSecret())
                .setExpiration(new Date(now.getTime()+ properties.getToken().getAccessTokenExpireTime()))
                .compact();
    }

    public String createRefreshToken(UserDto user) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonUser = mapper.writeValueAsString(user);
        Claims claims = Jwts.claims().setSubject(jsonUser);
        claims.put("roles", "ROLE_USER");
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, properties.getToken().getSecret())
                .setExpiration(new Date(now.getTime() + properties.getToken().getAccessTokenExpireTime()))
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(properties.getToken().getSecret())
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);
        logger.info("claims >> {}", principal.toString() + ", token= " + token + ",authorities= " + authorities.toString());
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token) {
        logger.info("validateToken Method >> {} ", token);
        try {
            Jwts.parser().setSigningKey(properties.getToken().getSecret()).parseClaimsJws(token);
            // black list에 저장된 access token 여부 확인
            int isBlackListToken = tokenService.isBlackListAccessToken(token);
            if(isBlackListToken > 0) {
                return false;
            }
            return true;
        } catch (MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String resolveToken(HttpServletRequest request) {
        // request header에서 access token정보 추출
        String bearerToken = request.getHeader(properties.getToken().getAccessTokenName());
        logger.info("bearerToken >>> {} ", bearerToken);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
