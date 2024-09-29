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
import com.opd.gateway.service.MessageService;
import com.opd.gateway.util.CryptoUtil;
import com.opd.gateway.util.ErroUtil;
import com.opd.gateway.util.IdUtil;

import jakarta.validation.Validator;

@RestController
@RequestMapping("/registro")
public class RegistroController {

	private UsuarioRepository usuarioRepository;
	private Validator validator;
	private MessageService messageService;

	public RegistroController(UsuarioRepository usuarioRepository, Validator validator, MessageService messageService) {
		super();
		this.usuarioRepository = usuarioRepository;
		this.validator = validator;
		this.messageService = messageService;
	}

	@PostMapping
	public @ResponseBody ResponseEntity<Object> registro(@RequestBody RegistroForm form) throws Exception {

		Set<Object> erros = ErroUtil.valida(validator, form);
		if (erros != null && !erros.isEmpty())
			return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);

		Usuario usuario = usuarioRepository.findByEmail(form.getEmail());
		if (usuario != null)
			return new ResponseEntity<>(messageService.getMessage("email.em.uso"), HttpStatus.FORBIDDEN);

		usuario = new Usuario();
		usuario.setId(IdUtil.id());
		usuario.setEmail(form.getEmail());
		usuario.setNome(form.getNome());
		usuario.setSenha(CryptoUtil.sha256(form.getSenha()));
		usuario = usuarioRepository.save(usuario);

		return ResponseEntity.ok(usuario);

	}

}