package br.com.escola.admin.controllers;

import br.com.escola.admin.dtos.DiretorDto;
import br.com.escola.admin.models.Diretor;
import br.com.escola.admin.services.DiretorService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/diretores")
public class DiretorController {

	private final DiretorService service;

	public DiretorController(DiretorService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<List<Diretor>> obterTodos() {
		return ResponseEntity.status(HttpStatus.OK).body(service.obterTodos());
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Diretor> obter(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(service.obter(id));
	}
	
	@PostMapping
	public ResponseEntity<Diretor> criar(@RequestBody @Valid DiretorDto diretorDto) {
		var diretor = new Diretor();
		BeanUtils.copyProperties(diretorDto, diretor);

		return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(diretor));
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<Diretor> atualizar(@PathVariable Long id, @RequestBody @Valid DiretorDto diretorDto) {
		var diretor = new Diretor();
		BeanUtils.copyProperties(diretorDto, diretor);

		return ResponseEntity.status(HttpStatus.OK).body(service.atualizar(id, diretor));
	}
	
	@DeleteMapping(path = "/{id}") 
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.deletar(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
}
