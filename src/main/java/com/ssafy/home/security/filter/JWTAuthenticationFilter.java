package com.ssafy.home.security.filter;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.home.security.dto.CustomUserDetails;
import com.ssafy.home.security.util.JWTUtil;
import com.ssafy.home.user.dto.User;
import com.ssafy.home.user.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    private final UserService userService;
    private final JWTUtil jwtUtil;

    // 조상의 생성자를 호출하기 위해 @RequeredArgsConstructor를 사용하지 않고 @Autowired를 사용
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, JWTUtil jwtUtil) {
        super(authenticationManager);
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.setFilterProcessesUrl("/api/auth/login"); // 로그인 URL 설정
        this.setUsernameParameter("username"); // email을 username으로 사용
        this.setPasswordParameter("password"); // password를 password로 사용
    }

    // 로그인 성공 시 실행하는 메소드 (JWT 발급)
    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authentication) {
        // TODO: 05-1. 로그인 성공 시 Authentication의 정보를 활용해 JWT를 발급한다.
    	CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
    	User user = details.getUser();
    	String accessToken = jwtUtil.createAccessToken(user);
    	String refreshToken = jwtUtil.createRefreshToken(user);
    	user.setRefreshToken(refreshToken);
    	userService.update(user);
    	
    	Map<String, String> reuslt = Map.of("status", "SUCCESS", "accessToken", accessToken, "refreshToken",refreshToken);
    	handleResult(response, reuslt, HttpStatus.OK);
        //  token 전달

        // END
        // TODO: 08-1. refresh token을 발급받아서 DB에 저장하고 추가로 클라이언트에게 전달한다.
    	
    }

    // 로그인 실패 시 실행하는 메소드
    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) {
        // 예외 처리는 한 곳에서 처리하도록 전달
        throw failed;
    }

    // 결과 전송 helper 메소드
    private void handleResult(HttpServletResponse response, Map<String, ?> data, HttpStatus status) {
        response.setContentType("application/json;charset=UTF-8");
        try {
            String jsonResponse = new ObjectMapper().writeValueAsString(data);
            response.setStatus(status.value());
            response.getWriter().write(jsonResponse);
        } catch (IOException e) {
            log.error("Error writing JSON response", e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
