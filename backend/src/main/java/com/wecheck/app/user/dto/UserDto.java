package com.wecheck.app.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String nickname;
    private Long typeCdx;
    private Date rgsDate;
    private Date udtDate;
    private Date delDate;
    private String loginId;
    private String password;
    private String phone;
    private String externalId;
    private Long socialCdx;
    private List<String> roles = Collections.emptyList();
}
