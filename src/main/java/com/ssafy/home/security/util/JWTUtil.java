package com.ssafy.home.security.util;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ssafy.home.user.dto.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JWTUtil {
	private final SecretKey key;

	public JWTUtil() {
		// 키를 랜덤하게 생성
		key = Jwts.SIG.HS256.key().build(); // secret key
		log.debug("jwt secret key: {}", Arrays.toString(key.getEncoded()));
	}

	@Value("${ssafy.jwt.access-expmin}")
	private long accessExpMin;

	@Value("${ssafy.jwt.refresh-expmin}")
	private long refreshExpMin;

	public String createAccessToken(User user) {
		// null 값을 직렬화 시킬 수 없다.
		if (user.getRole() == null) {
			user.setRole("USER");
		}
		if(user.getAptSeq() != null) {
			return create("accessToken", accessExpMin,
					Map.of("username", user.getUsername(), "name", user.getName(), "role", user.getRole(), "aptSeq", user.getAptSeq(), "residence", user.getResidence()));
		}else {
			return create("accessToken", accessExpMin,
					Map.of("username", user.getUsername(), "name", user.getName(), "role", user.getRole()));
		}
		
	}

	public String createRefreshToken(User user) {
		if(user.getAptSeq() != null) {
			return create("refreshToken", refreshExpMin, Map.of("username", user.getUsername(), "name", user.getName(), "role", user.getRole(), "aptSeq", user.getAptSeq(), "residence", user.getResidence()));	
		}else {
			return create("refreshToken", refreshExpMin, Map.of("username", user.getUsername(), "name", user.getName(), "role", user.getRole()));
		}
		
	}

	/**
	 * JWT를 반환한다.
	 * 
	 * @param subject   token의 제목(accessToken, refreshToken)
	 * @param expireMin
	 * @param member    회원 정보
	 * @return
	 */
	public String create(String subject, long expireMin, Map<String, Object> claims) {

		Date expireDate = new Date(System.currentTimeMillis() + 1000 * 60 * expireMin);
		// TODO: 03-1. JWT를 생성해서 반환하세요.
		// claim에는 email, name, role을 담고, 만료일은 expireDate로 설정하세요.
		String token = Jwts.builder().subject(subject).claims(claims).expiration(expireDate).signWith(key).compact();
		log.debug("token 생성 : {}", token);
		return token;

		// END
	}

	/**
	 * 토큰 검증 및 claim 정보 반환
	 * 
	 * @param jwt
	 * @return
	 * @throws ExpireJwtException: 형식은 적합하지만 토큰의 유효기간이 지난 경우 MalformedJwtException:
	 *                             형식이 잘못된 토큰을 이용하려는 경우 SignatureException: 훼손된 토큰을
	 *                             이용하려는 경우
	 */
	public Claims getClaims(String jwt) {
		// TODO: 03-2. JWT토큰을 검증하고 claim 정보를 반환하는 코드를 살펴보세요.
		var parser = Jwts.parser().verifyWith(key).build();
		var jws = parser.parseSignedClaims(jwt);
		log.debug("claims: {}", jws.getPayload());
		return jws.getPayload();

		// END
	}
}
