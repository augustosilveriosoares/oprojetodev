package com.opd.core.service;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;


@Service
public class JwtService {

	@Value("${jwt.token.secret}")
	private String secret;
	private static final String HEADER_STRING = "Authorization";
	private static final String PREFIX = "Bearer";

	public Claims getClaims(HttpServletRequest request) throws Exception {

		String authorizationHeader = request.getHeader(HEADER_STRING);
		if (authorizationHeader == null)
			return null;

		authorizationHeader = authorizationHeader.replace(PREFIX, "").trim();

		byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
		SecretKey key = Keys.hmacShaKeyFor(keyBytes);

		Claims claims = null;
		try {
			claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(authorizationHeader).getPayload();
		} catch (Exception ig) {
		}
		return claims;

	}

}