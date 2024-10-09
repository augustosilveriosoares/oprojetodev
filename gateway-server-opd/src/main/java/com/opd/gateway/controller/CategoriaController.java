package com.opd.gateway.controller;

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

import com.opd.gateway.model.Categoria;
import com.opd.gateway.repository.CategoriaRepository;
import com.opd.gateway.util.ErroUtil;
import com.opd.gateway.util.IdUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	private CategoriaRepository categoriaRepository;

	public CategoriaController(CategoriaRepository categoriaRepository) {
		super();
		this.categoriaRepository = categoriaRepository;
	}

	@GetMapping
	public ResponseEntity<Object> lista() {

		List<CategoriaForm> lista = new ArrayList<>();

		List<Categoria> categorias = (List<Categoria>) categoriaRepository.findAll();
		for (Categoria categoria : categorias) {
			lista.add(new CategoriaForm(categoria));
		}

		return ResponseEntity.ok(lista);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> lista(@PathVariable(name = "id") String id) {

		Optional<Categoria> op = categoriaRepository.findById(id);
		if (op.isEmpty())
			return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);

		return ResponseEntity.ok(new CategoriaForm(op.get()));
	}

	@PostMapping
	public @ResponseBody ResponseEntity<Object> adiciona(@RequestBody @Valid CategoriaForm form, BindingResult result) {

		if (result.hasErrors())
			return new ResponseEntity<>(ErroUtil.formata(result.getFieldErrors()), HttpStatus.BAD_REQUEST);

		Categoria categoria = new Categoria();
		categoria.setId(IdUtil.id());
		categoria.setNome(form.getNome());
		categoria = categoriaRepository.save(categoria);

		return ResponseEntity.ok(new CategoriaForm(categoria));
	}

	@PutMapping("/{id}")
	public @ResponseBody ResponseEntity<Object> atualiza(@PathVariable(name = "id") String id,
			@RequestBody @Valid CategoriaForm form, BindingResult result) {

		if (result.hasErrors())
			return new ResponseEntity<>(ErroUtil.formata(result.getFieldErrors()), HttpStatus.BAD_REQUEST);

		Optional<Categoria> op = categoriaRepository.findById(id);
		if (op.isEmpty())
			return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);

		Categoria categoria = op.get();
		categoria.setNome(form.getNome());
		categoria = categoriaRepository.save(categoria);

		return ResponseEntity.ok(new CategoriaForm(categoria));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable(name = "id") String id) {
		categoriaRepository.deleteById(id);
		return ResponseEntity.ok(id);
	}

}