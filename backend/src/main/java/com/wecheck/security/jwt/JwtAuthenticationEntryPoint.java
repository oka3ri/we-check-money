package com.wecheck.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wecheck.security.code.TokenErrCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String exception = (String)request.getAttribute("exception");

        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        Map<String, String> errorDetails = new HashMap<>();
        // NOTE: Access Token 만료된 경우
        if(exception.equals(TokenErrCode.TOKEN_001.getCode())) {
            errorDetails.put("code", TokenErrCode.TOKEN_001.getCode());
            errorDetails.put("message", TokenErrCode.TOKEN_001.getMessage());
        } else {
            errorDetails.put("code", TokenErrCode.EMPTY_TOKEN.getCode());
            errorDetails.put("message", TokenErrCode.EMPTY_TOKEN.getMessage());
        }
        objectMapper.writeValue(response.getWriter(), errorDetails);
    }
}
