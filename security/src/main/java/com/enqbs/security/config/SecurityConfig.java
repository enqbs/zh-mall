package com.enqbs.security.config;

import com.enqbs.security.filter.JwtAuthenticationTokenFilter;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Resource
    private IgnoreUrlsProperties ignoreUrlsProperties;

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private AccessDeniedHandler accessDeniedHandler;

    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        try {
            return http
                    .csrf(AbstractHttpConfigurer::disable)                                      // 跨域支持
                    .sessionManagement(s -> s
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)             // 不需要 session
                    )
                    .authorizeHttpRequests(a -> a
                            .requestMatchers(ignoreUrlsProperties.getAnonymous()).anonymous()   // 只允许未登录访问 url
                            .requestMatchers(ignoreUrlsProperties.getPermit()).permitAll()      // 允许匿名访问 url
                            .anyRequest().authenticated()                                       // 所有接口拦截
                    )
                    .exceptionHandling(e -> e
                            .authenticationEntryPoint(authenticationEntryPoint)                 // 自定义认证异常处理
                            .accessDeniedHandler(accessDeniedHandler)                           // 自定义授权异常处理
                    )
                    .addFilterBefore(jwtAuthenticationTokenFilter,                              // 自定义过滤器
                            UsernamePasswordAuthenticationFilter.class
                    )
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
