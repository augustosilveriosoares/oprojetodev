package com.opd.gateway.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.opd.gateway.model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${jwt.token.secret}")
	private String secret = "_7hCFHDLM8ae0GsE0rWeaE_8gFYrM33_Wf0hV6XHif-FWmb_V23nL6To6P3AJJIIruY6-Iwjzh_ZsnzZsbSfXQ";
	@Value("${jwt.token.validity}")
	private long tokenValidity = 864000000;

	public String buildToken(Usuario usuario) {

		byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
		SecretKey key = Keys.hmacShaKeyFor(keyBytes);

		return Jwts
				.builder()
				.claims()
				.subject(usuario.getEmail())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + tokenValidity))
				.and()
				.signWith(key)
				.compact();
	}

	public Claims getClaims(String token) {

		byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
		SecretKey key = Keys.hmacShaKeyFor(keyBytes);

		return Jwts
				.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public static void main(String[] args) {
		
		Usuario usuario = new Usuario();
		usuario.setEmail("admin@oprojetodev.com.br");
		
		JwtService service = new JwtService();
		String token = service.buildToken(usuario);
		System.out.println(token);
		
		Claims claims = service.getClaims(token);
		System.out.println(claims.getSubject());
	}

}