package com.wecheck.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenDto {
    private String refreshToken;
    private Long userId;
    private String expiredDate;
}
