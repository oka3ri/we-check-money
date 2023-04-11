package com.wecheck.security.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TokenErrCode {
    EMPTY_TOKEN("EMPTY_TOKEN", "유효한 토큰 정보가 없습니다."),
    AUTH_DENIED("AUTH_DENIED", "접근 권한이 없습니다."),
    TOKEN_001("TOKEN_001", "만료된 Access Token 입니다."),
    TOKEN_002("TOKEN_002", "만료된 Refresh Token 입니다."),
    TOKEN_003("TOKEN_003", "인증 정보 갱신이 불가합니다. 다시 로그인하세요."),
    TOKEN_004("TOKEN_004", "인증 세션이 만료되었습니다. 다시 로그인하세요.");

    @Getter
    private final String code;
    @Getter
    private final String message;
}
