package com.opd.core.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.opd.core.model.Categoria;
import com.opd.core.repository.CategoriaRepository;



@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	
	@GetMapping
	public ResponseEntity<Object> lista() {
		
		List<Categoria> lista = (List<Categoria>) categoriaRepository.findAll();
		return ResponseEntity.ok(lista);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> lista(@PathVariable(name = "id") String id) {

		Optional<Categoria> op = categoriaRepository.findById(id);
		if (op.isEmpty())
			return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);

		Categoria c = op.get();
		return ResponseEntity.ok(c);

	}

	@PostMapping
	public @ResponseBody ResponseEntity<Object> adiciona(@RequestBody Categoria categoria) {

		categoria = categoriaRepository.save(categoria);
		return ResponseEntity.ok(categoria);
	}

	@PutMapping("/{id}")
	public @ResponseBody ResponseEntity<Object> atualiza(@PathVariable(name = "id") String id,
			@RequestBody Categoria categoria) {

		Optional<Categoria> op = categoriaRepository.findById(id);
		if (op.isEmpty())
			return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);

		Categoria original = op.get();
		original.setNome(categoria.getNome());
		original = categoriaRepository.save(original);

		return ResponseEntity.ok(original);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable(name = "id") String id) {

		categoriaRepository.deleteById(id);
		return ResponseEntity.ok(id);
	}

}