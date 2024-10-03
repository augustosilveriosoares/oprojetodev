package com.opd.gateway.controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.opd.gateway.model.Usuario;
import com.opd.gateway.repository.UsuarioRepository;
import com.opd.gateway.service.JwtService;
import com.opd.gateway.util.CryptoUtil;
import com.opd.gateway.util.ErroUtil;

import jakarta.validation.Validator;

@RestController
@RequestMapping("/login")
public class LoginController {

	private UsuarioRepository usuarioRepository;
	private Validator validator;
	private JwtService jwtService;

	public LoginController(UsuarioRepository usuarioRepository, Validator validator, JwtService jwtService) {
		super();
		this.usuarioRepository = usuarioRepository;
		this.validator = validator;
		this.jwtService = jwtService;
	}

	@PostMapping
	public @ResponseBody ResponseEntity<Object> login(@RequestBody LoginForm form) throws Exception {

		Set<Object> erros = ErroUtil.valida(validator, form);
		if (erros != null && !erros.isEmpty())
			return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);

		Usuario usuario = usuarioRepository.findByEmail(form.getEmail());
		if (usuario == null)
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

		if (!usuario.getSenha().equals(CryptoUtil.sha256(form.getSenha())))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		TokenForm token = new TokenForm();
		token.setToken(jwtService.buildToken(usuario));

		return new ResponseEntity<>(token, HttpStatus.OK);
	}

}