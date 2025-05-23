package com.ssafy.home.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.home.security.service.CustomUserDetailService;
import com.ssafy.home.security.util.JWTUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTVerificationFilter extends OncePerRequestFilter {
	
	private final JWTUtil jwtUtil;
	private final CustomUserDetailService userDetailsService;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.debug("JWTVerificationFilter.doFilterInternal() called");
		// TODO: 04-2. JWT 토큰을 검증하고 인증 정보를 SecurityContextHolder에 저장한다.
		String token = extractToken(request);
		if(token == null ) {
			filterChain.doFilter(request, response);
			return;
		}
		// 토큰 검증 및 사용자 정보 추출 - 토큰에 문제 없다면 사용자 정보는 신뢰할만하다.
		Claims claims = jwtUtil.getClaims(token);
		UserDetails details = userDetailsService.loadUserByUsername(claims.get("username").toString());
		// claim 정보를 이용한 사용자 정보 조회
		var authentication = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
		// UserNameAuthenticationToken 생성 및 SecurityContextHolder에 저장
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// 다음 filter로 요청을 전달
		filterChain.doFilter(request, response);
		// END
	}

	private String extractToken(HttpServletRequest request) {
		// TODO: 04-1. Authorization 헤더가 'Bearer '로 시작하는지 확인하고, Bearer를 제거한 토큰을 반환한다.
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer")) {
			return header.substring(7);
		}
		return null;
		// END
	}
}
