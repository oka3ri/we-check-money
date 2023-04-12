package com.wecheck.security.jwt;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("JwtFilter doFilter >>>> ");
        String requestURI = request.getRequestURI();
        // NOTE: 회원가입 페이지 접근시 하위 로직 생략
        if(requestURI.equals("/auth/signup") || requestURI.equals("/auth/login")) {
            logger.info("access signup page >>>> ");
            filterChain.doFilter(request, response);
            return;
        }
        // access-token 추출
        String token = jwtTokenProvider.resolveToken(request);
        boolean isValidToken = jwtTokenProvider.validateTokenWithException(token, request);
        // 토큰 유효성 체크
        if (StringUtils.hasText(token) && isValidToken) {
            // securityContext에 token 저장
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        } else {
            logger.info("유효한 Access Token이 없습니다, uri: {}", requestURI);
        }

        filterChain.doFilter(request, response);
    }
}
