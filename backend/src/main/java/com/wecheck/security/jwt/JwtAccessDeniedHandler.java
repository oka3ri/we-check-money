package com.wecheck.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wecheck.security.code.TokenErrCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        //필요한 권한이 없이 접근하려 할때 403
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("code", TokenErrCode.AUTH_DENIED.getCode());
        errorDetails.put("message", TokenErrCode.AUTH_DENIED.getMessage());
        objectMapper.writeValue(response.getWriter(), errorDetails);
    }
}