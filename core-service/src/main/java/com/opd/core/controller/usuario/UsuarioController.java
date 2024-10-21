package com.opd.core.controller.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.opd.core.model.Usuario;
import com.opd.core.repository.UsuarioRepository;
import com.opd.core.util.ErroUtil;
import com.opd.core.util.IdUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	private UsuarioRepository usuarioRepository;

	public UsuarioController(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}

	@GetMapping
	public ResponseEntity<Object> lista() {

		List<UsuarioForm> lista = new ArrayList<>();

		List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();
		for (Usuario usuario : usuarios) {
			lista.add(new UsuarioForm(usuario));
		}

		return ResponseEntity.ok(lista);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> lista(@PathVariable(name = "id") String id) {

		Optional<Usuario> op = usuarioRepository.findById(id);
		if (op.isEmpty())
			return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);

		return ResponseEntity.ok(new UsuarioForm(op.get()));
	}

	@PostMapping
	public @ResponseBody ResponseEntity<Object> adiciona(@RequestBody @Valid UsuarioForm form, BindingResult result) {

		if (result.hasErrors())
			return new ResponseEntity<>(ErroUtil.formata(result.getFieldErrors()), HttpStatus.BAD_REQUEST);

		Usuario usuario = new Usuario();
		usuario.setId(IdUtil.id());
		usuario.setNome(form.getNome());
		usuario.setEmail(form.getEmail());
		usuario = usuarioRepository.save(usuario);

		return ResponseEntity.ok(new UsuarioForm(usuario));
	}

	@PutMapping("/{id}")
	public @ResponseBody ResponseEntity<Object> atualiza(@PathVariable(name = "id") String id,
			@RequestBody @Valid UsuarioForm form, BindingResult result) {

		if (result.hasErrors())
			return new ResponseEntity<>(ErroUtil.formata(result.getFieldErrors()), HttpStatus.BAD_REQUEST);

		Optional<Usuario> op = usuarioRepository.findById(id);
		if (op.isEmpty())
			return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);

		Usuario usuario = op.get();
		usuario.setNome(form.getNome());
		usuario.setEmail(form.getEmail());
		usuario = usuarioRepository.save(usuario);

		return ResponseEntity.ok(new UsuarioForm(usuario));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable(name = "id") String id) {
		usuarioRepository.deleteById(id);
		return ResponseEntity.ok(id);
	}

}