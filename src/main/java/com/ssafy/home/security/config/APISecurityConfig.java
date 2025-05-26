package com.ssafy.home.security.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ssafy.home.security.filter.JWTAuthenticationFilter;
import com.ssafy.home.security.filter.JWTVerificationFilter;
import com.ssafy.home.security.filter.SecurityExceptionHandlingFilter;
import com.ssafy.home.security.service.CustomUserDetailService;

@Configuration
public class APISecurityConfig {

    @Bean
    // Spring Security 5.4 이상에서 AuthenticationManager를 Bean으로 사용하려면 명시적 등록 필요
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(
            HttpSecurity http,
            @Qualifier("corsConfigurationSource") CorsConfigurationSource corsConfig,
            CustomUserDetailService userDetailsService,
            JWTAuthenticationFilter authFilter,
            JWTVerificationFilter jwtVerifyFilter,
            SecurityExceptionHandlingFilter exceptionFilter)
            throws Exception {
        // TODO: 06-1. SecurityFilterChain을 생성한다.
        //  이 filter chain은 /api/**를 대상으로 하며 cors, userdetails, csrf, session 관련 설정을 해보자.
    	http.securityMatcher("/api/**")
    		.cors(t -> t.configurationSource(corsConfig))
    		.userDetailsService(userDetailsService)
    		.csrf(csrf -> csrf.disable())
    		.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        //  /api/v1/etc/**, /api/auth/** 경로는 permitall 하고 나머지는(/api/v1/members) 인증을 요청한다.
    	http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/auth/**").permitAll()
    													.requestMatchers("/api/users").authenticated()
    													.anyRequest().permitAll());
        //  적절한 순서로 Filter의 위치를 지정한다.
    	http.addFilterBefore(jwtVerifyFilter, UsernamePasswordAuthenticationFilter.class)//UsernamePasswordAuthenticationFilter 앞에
    		.addFilterAt(authFilter, UsernamePasswordAuthenticationFilter.class)//UsernamePasswordAuthenticationFilter를 대체
    		.addFilterBefore(exceptionFilter,JWTVerificationFilter.class);
        // END
        return http.build();
    }

    // TODO: 06-2. Filter 수준에서 동작하기 위한 CorsConfigurationSource를 구성하고 적용하자.
    // WebMvcConfigurer는 cors 설정은 무의미
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        source.registerCorsConfiguration("/user", configuration);

        return source;
    }
}
