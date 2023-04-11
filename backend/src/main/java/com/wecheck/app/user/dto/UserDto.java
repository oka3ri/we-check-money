package com.wecheck.app.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String nickname;
    private Long typeCdx;
    private String rgsDate;
    private String udtDate;
    private String delDate;
    private String loginId;
    private String password;
    private String phone;
    private String externalId;
    private Long socialCdx;
}
