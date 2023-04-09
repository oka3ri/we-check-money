package com.wecheck.common.configuration;

import com.wecheck.common.properties.CommonProperties;
import com.wecheck.security.jwt.JwtAccessDeniedHandler;
import com.wecheck.security.jwt.JwtAuthenticationEntryPoint;
import com.wecheck.security.jwt.JwtFilter;
import com.wecheck.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfiguration {

    private final CommonProperties properties;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    public WebSecurityConfiguration(CommonProperties properties, JwtTokenProvider jwtTokenProvider
            , JwtAccessDeniedHandler jwtAccessDeniedHandler, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.properties = properties;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().antMatchers(properties.getSecurity().getIgnores()));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Enable CORS and disable CSRF
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                // 401, 403 설정
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                // session 사용안함
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 접근권한 설정
                .authorizeHttpRequests()
                .antMatchers("/api/signup").permitAll()
                .antMatchers("/api/auth/login/general").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                // jwt token filter 설정
                .addFilterBefore(new JwtFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
                // logout
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
//                .clearAuthentication(true);
//
//        // iframe option
//        switch (properties.getSecurity().getIframeOption()) {
//            case "same-origin":
//                http = http.headers().frameOptions().sameOrigin().and();
//                break;
//            case "deny":
//                http = http.headers().frameOptions().deny().and();
//                break;
//            case "disable":
//                http = http.headers().frameOptions().disable().and();
//                break;
//            default:
//                break;
//        }
//
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(properties.getSecurity().getCors().getAllowedOrigins()));
        configuration.setAllowedMethods(Arrays.asList(properties.getSecurity().getCors().getAllowedMethods()));
        configuration.setAllowedHeaders(Arrays.asList(properties.getSecurity().getCors().getAllowedHeaders()));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(properties.getSecurity().getCors().getPattern(), configuration);

        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
