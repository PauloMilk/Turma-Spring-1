package br.com.escola.admin.controllers;

import br.com.escola.admin.dtos.ProfessorDto;
import br.com.escola.admin.models.Professor;
import br.com.escola.admin.services.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/professores")
public class ProfessorController {

	private final ProfessorService service;
	
	public ProfessorController(ProfessorService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<Professor>> obterTodos() {
		return ResponseEntity.status(HttpStatus.OK).body(service.obterTodos());
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Professor> obter(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(service.obter(id));
	}
	
	@PostMapping
	public ResponseEntity<Professor> salvar(@RequestBody @Valid ProfessorDto professorDto) {
		var professor = new Professor();
		BeanUtils.copyProperties(professorDto, professor);

		return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(professor));
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<Professor> atualizar(@PathVariable Long id, @RequestBody @Valid ProfessorDto professorDto) {
		var professor = new Professor();
		BeanUtils.copyProperties(professorDto, professor);

		return ResponseEntity.status(HttpStatus.OK).body(service.atualizar(id, professor));
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		service.deletar(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
}
