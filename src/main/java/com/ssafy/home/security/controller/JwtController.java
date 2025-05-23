package com.ssafy.home.security.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.exception.CustomException;
import com.ssafy.home.common.exception.ErrorCode;
import com.ssafy.home.common.response.ApiResponse;
import com.ssafy.home.security.util.JWTUtil;
import com.ssafy.home.user.dto.User;
import com.ssafy.home.user.service.UserService;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class JwtController {

    private final JWTUtil jwtUtil;
    private final UserService userService;

    // TODO: 09-1. /api/auth/refresh요청 처리를 위한 handler 메서드를 살펴보자.
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<Map>> refreshAccessToken(@RequestHeader("Refresh-Token") String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
        	throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
        // 1. Refresh Token 유효성 검증 (JWT 자체) 및 이메일 추출
        Map<String, Object> claims = jwtUtil.getClaims(refreshToken);
        String username = (String) claims.get("username"); // Refresh Token 생성 시 넣었던 클레임 키

        if (username == null) {
            throw new JwtException("Invalid refresh token: email claim missing");
        }

        // 2. DB에서 사용자 조회 및 Refresh Token 일치 여부 확인
        User user = userService.select(username);

        if (user == null || user.getRefreshToken() == null || !user.getRefreshToken().equals(refreshToken)) {
            log.warn("Invalid or mismatched refresh token for user: {}", username);
            // 보안: DB의 토큰과 불일치 시, 해당 사용자의 DB 토큰을 무효화(null 처리)하는 것도 고려
            // memberService.invalidateRefreshToken(email);
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        // 3. 새 Access Token 생성
        String newAccessToken = jwtUtil.createAccessToken(user);

        // 4. Refresh Token Rotation: 새 Refresh Token 생성 및 DB 업데이트 - 보안 상 권장
        String newRefreshToken = jwtUtil.createRefreshToken(user);
        user.setRefreshToken(newRefreshToken); // Member 객체에 새 Refresh Token 설정
        userService.update(user); // DB에 새 Refresh Token 저장

        // 5. 새 토큰들을 응답으로 반환
        return ResponseEntity.ok(ApiResponse.success(Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken))); 
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Map>> logout(@RequestHeader("Refresh-Token") String refreshToken) {
        // TODO: 11-1. /api/auth/logout 요청 시 refresh token에 해당하는 사용자 정보를 조회해서 정보를 수정하자. 
    	 if (refreshToken == null || refreshToken.isEmpty()) {
    		 throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
         }
         // 1. Refresh Token 유효성 검증 (JWT 자체) 및 이메일 추출
         Map<String, Object> claims = jwtUtil.getClaims(refreshToken);
         String username = (String) claims.get("username"); // Refresh Token 생성 시 넣었던 클레임 키

         if (username == null) {
             throw new JwtException("Invalid refresh token: username claim missing");
         }

         // 2. DB에서 사용자 조회 및 Refresh Token 일치 여부 확인
         User user = userService.select(username);
         user.setRefreshToken(null);
         userService.update(user);
         return ResponseEntity.ok(ApiResponse.success(Map.of("accessToken", "", "refreshToken", ""))); 
        // END
    }
}
