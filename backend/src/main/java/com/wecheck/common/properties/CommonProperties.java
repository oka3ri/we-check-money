package com.wecheck.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "properties.common")
public class CommonProperties {
    private SecurityProperties security;
    private TokenProperties token;

    @Data
    @ConfigurationProperties(prefix = "security")
    public static class SecurityProperties {
        private String[] ignores;
        private String[] permitAlls;
        private String redirectUrl;
        private CorsProperties cors;
        private String iframeOption;
        private String sameSite;

    }

    @Data
    @ConfigurationProperties(prefix = "token")
    public static class TokenProperties {
        private String prefix;
        private String tokenName;
        private Long accessTokenExpireTime;
        private Long refreshTokenExpireTime;
        private String secret;
    }

    @Data
    @ConfigurationProperties(prefix = "cors")
    public static class CorsProperties {
        private String pattern;
        private String[] allowedOrigins;
        private String[] allowedMethods;
        private String[] allowedHeaders;
    }
}
