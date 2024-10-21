package com.opd.core.controller.caixa;

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

import com.opd.core.model.Caixa;
import com.opd.core.model.Usuario;
import com.opd.core.repository.CaixaRepository;
import com.opd.core.repository.UsuarioRepository;
import com.opd.core.util.ErroUtil;
import com.opd.core.util.IdUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/caixas")
public class CaixaController {

	private CaixaRepository caixaRepository;
	private UsuarioRepository usuarioRepository;

	public CaixaController(CaixaRepository caixaRepository, UsuarioRepository usuarioRepository) {
		super();
		this.caixaRepository = caixaRepository;
		this.usuarioRepository = usuarioRepository;
	}

	@GetMapping
	public ResponseEntity<Object> lista() {

		List<CaixaForm> lista = new ArrayList<>();

		List<Caixa> caixas = (List<Caixa>) caixaRepository.findAll();
		for (Caixa caixa : caixas) {
			lista.add(new CaixaForm(caixa));
		}

		return ResponseEntity.ok(lista);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> lista(@PathVariable(name = "id") String id) {

		Optional<Caixa> op = caixaRepository.findById(id);
		if (op.isEmpty())
			return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);

		return ResponseEntity.ok(new CaixaForm(op.get()));
	}

	@PostMapping
	public @ResponseBody ResponseEntity<Object> adiciona(@RequestBody @Valid CaixaForm form, BindingResult result) {

		if (result.hasErrors())
			return new ResponseEntity<>(ErroUtil.formata(result.getFieldErrors()), HttpStatus.BAD_REQUEST);

		Caixa caixa = new Caixa();
		caixa.setId(IdUtil.id());
		caixa.setNome(form.getNome());
		caixa.setSaldo(form.getSaldo());
		caixa.setTipo(form.getTipo());

		Optional<Usuario> op = usuarioRepository.findById(form.getUsuarioId());
		if (op.isEmpty())
			return new ResponseEntity<>(form.getUsuarioId(), HttpStatus.NO_CONTENT);

		caixa.setUsuario(op.get());

		caixa = caixaRepository.save(caixa);
		return ResponseEntity.ok(new CaixaForm(caixa));
	}

	@PutMapping("/{id}")
	public @ResponseBody ResponseEntity<Object> atualiza(@PathVariable(name = "id") String id,
			@RequestBody @Valid CaixaForm form, BindingResult result) {

		if (result.hasErrors())
			return new ResponseEntity<>(ErroUtil.formata(result.getFieldErrors()), HttpStatus.BAD_REQUEST);

		Optional<Caixa> op = caixaRepository.findById(id);
		if (op.isEmpty())
			return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);

		Caixa caixa = op.get();
		caixa.setNome(form.getNome());
		caixa.setSaldo(form.getSaldo());
		caixa.setTipo(form.getTipo());

		Optional<Usuario> opU = usuarioRepository.findById(form.getUsuarioId());
		if (opU.isEmpty())
			return new ResponseEntity<>(form.getUsuarioId(), HttpStatus.NO_CONTENT);

		caixa.setUsuario(opU.get());

		caixa = caixaRepository.save(caixa);

		return ResponseEntity.ok(new CaixaForm(caixa));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable(name = "id") String id) {
		caixaRepository.deleteById(id);
		return ResponseEntity.ok(id);
	}

}