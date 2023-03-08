package br.com.escola.admin.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.escola.admin.models.Professor;
import br.com.escola.admin.services.ProfessorService;

@RestController
public class ProfessorController {

	private final ProfessorService service;
	
	public ProfessorController(ProfessorService service) {
		this.service = service;
	}

	@GetMapping(path = "/professores")
	public List<Professor> obterTodos() {
		return service.obterTodos();
	}
	
	@GetMapping(path = "/professores/{id}")
	public Professor obter(@PathVariable Long id) {
		return service.obter(id);
	}
	
	@PostMapping(path = "/professores")
	public Professor salvar(@RequestBody Professor professor) {
		return service.salvar(professor);
	}
	
	@PutMapping(path = "/professores/{id}")
	public Professor atualizar(@PathVariable Long id, @RequestBody Professor professor) {
		Professor professorAtualizado = service.atualizar(id, professor);
		return professorAtualizado;
	}
	
	@DeleteMapping(path = "/professores/{id}")
	public void deletar(@PathVariable Long id) {
		service.deletar(id);
	}
	
}
