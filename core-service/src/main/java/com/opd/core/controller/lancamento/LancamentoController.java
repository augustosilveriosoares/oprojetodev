package com.opd.core.controller.lancamento;

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

import com.opd.core.annotation.UserInfo;
import com.opd.core.annotation.WithUser;
import com.opd.core.model.Caixa;
import com.opd.core.model.Categoria;
import com.opd.core.model.Lancamento;
import com.opd.core.model.Usuario;
import com.opd.core.repository.CaixaRepository;
import com.opd.core.repository.CategoriaRepository;
import com.opd.core.repository.LancamentoRepository;
import com.opd.core.repository.UsuarioRepository;
import com.opd.core.util.ErroUtil;
import com.opd.core.util.IdUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {

	private LancamentoRepository lancamentoRepository;
	private UsuarioRepository usuarioRepository;
	private CaixaRepository caixaRepository;
	private CategoriaRepository categoriaRepository;

	public LancamentoController(LancamentoRepository lancamentoRepository, UsuarioRepository usuarioRepository,
			CaixaRepository caixaRepository, CategoriaRepository categoriaRepository) {
		super();
		this.lancamentoRepository = lancamentoRepository;
		this.usuarioRepository = usuarioRepository;
		this.caixaRepository = caixaRepository;
		this.categoriaRepository = categoriaRepository;
	}

	@GetMapping
	public ResponseEntity<Object> lista(@WithUser UserInfo info) {

		List<LancamentoForm> lista = new ArrayList<>();

		if (info.hasUser()) {

			Usuario usuario = usuarioRepository.findByEmail(info.getPrincipal());
			if (usuario == null)
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

			List<Lancamento> lancamentos = lancamentoRepository.findByUsuarioId(usuario.getId());
			for (Lancamento lancamento : lancamentos) {
				lista.add(new LancamentoForm(lancamento));
			}

		}

		return ResponseEntity.ok(lista);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> lista(@PathVariable(name = "id") String id, @WithUser UserInfo info) {

		if (!info.hasUser())
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

		Usuario usuario = usuarioRepository.findByEmail(info.getPrincipal());
		if (usuario == null)
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

		Optional<Lancamento> op = lancamentoRepository.findById(id);
		if (op.isEmpty())
			return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);

		Lancamento lancamento = op.get();
		if (!lancamento.getUsuario().equals(usuario))
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

		return ResponseEntity.ok(new LancamentoForm(lancamento));
	}

	@PostMapping
	public @ResponseBody ResponseEntity<Object> adiciona(@RequestBody @Valid LancamentoForm form, BindingResult result,
			@WithUser UserInfo info) {

		if (result.hasErrors())
			return new ResponseEntity<>(ErroUtil.formata(result.getFieldErrors()), HttpStatus.BAD_REQUEST);

		if (!info.hasUser())
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

		Usuario usuario = usuarioRepository.findByEmail(info.getPrincipal());
		if (usuario == null)
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

		Lancamento lancamento = new Lancamento();
		lancamento.setId(IdUtil.id());
		lancamento.setUsuario(usuario);
		lancamento.setData(form.getData());
		lancamento.setValor(form.getValor());
		lancamento.setDescricao(form.getDescricao());
		lancamento.setTipo(form.getTipo());

		Optional<Caixa> opCx = caixaRepository.findById(form.getCaixaId());
		if (opCx.isEmpty())
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

		lancamento.setCaixa(opCx.get());

		Optional<Categoria> opCat = categoriaRepository.findById(form.getCaixaId());
		if (opCat.isEmpty())
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

		lancamento.setCategoria(opCat.get());

		lancamento = lancamentoRepository.save(lancamento);

		lancamento.setUsuario(usuario);

		lancamento = lancamentoRepository.save(lancamento);
		return ResponseEntity.ok(new LancamentoForm(lancamento));
	}

	@PutMapping("/{id}")
	public @ResponseBody ResponseEntity<Object> atualiza(@PathVariable(name = "id") String id,
			@RequestBody @Valid LancamentoForm form, BindingResult result, @WithUser UserInfo info) {

		if (!info.hasUser())
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

		Usuario usuario = usuarioRepository.findByEmail(info.getPrincipal());
		if (usuario == null)
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

		if (result.hasErrors())
			return new ResponseEntity<>(ErroUtil.formata(result.getFieldErrors()), HttpStatus.BAD_REQUEST);

		Optional<Lancamento> op = lancamentoRepository.findById(id);
		if (op.isEmpty())
			return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);

		Lancamento lancamento = op.get();
		if (!lancamento.getUsuario().equals(usuario))
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

		lancamento.setData(form.getData());
		lancamento.setValor(form.getValor());
		lancamento.setDescricao(form.getDescricao());
		lancamento.setTipo(form.getTipo());

		Optional<Caixa> opCx = caixaRepository.findById(form.getCaixaId());
		if (opCx.isEmpty())
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

		lancamento.setCaixa(opCx.get());

		Optional<Categoria> opCat = categoriaRepository.findById(form.getCaixaId());
		if (opCat.isEmpty())
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

		lancamento.setCategoria(opCat.get());

		lancamento = lancamentoRepository.save(lancamento);

		return ResponseEntity.ok(new LancamentoForm(lancamento));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable(name = "id") String id, @WithUser UserInfo info) {

		if (!info.hasUser())
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

		Usuario usuario = usuarioRepository.findByEmail(info.getPrincipal());
		if (usuario == null)
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

		Optional<Lancamento> op = lancamentoRepository.findById(id);
		if (op.isEmpty())
			return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);

		Lancamento lancamento = op.get();
		if (!lancamento.getUsuario().equals(usuario))
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

		lancamentoRepository.deleteById(id);
		return ResponseEntity.ok(id);
	}

}