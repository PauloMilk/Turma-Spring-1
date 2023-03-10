package br.com.escola.admin.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.escola.admin.models.Diretor;
import br.com.escola.admin.services.DiretorService;

@RestController
@RequestMapping(value = "/diretores")
public class DiretorController {

	private final DiretorService service;

	public DiretorController(DiretorService service) {
		this.service = service;
	}
	
	@GetMapping
	public List<Diretor> obterTodos() {
		return service.obterTodos();
	}
	
	@GetMapping(path = "/{id}")
	public Diretor obter(@PathVariable Long id) {
		return service.obter(id);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Diretor criar(@RequestBody Diretor diretor) {
		return service.criar(diretor);
	}
	
	@PutMapping(path = "/{id}")
	public Diretor atualizar(@PathVariable Long id, @RequestBody Diretor diretor) {
		return service.atualizar(id, diretor);
	}
	
	@DeleteMapping(path = "/{id}") 
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.deletar(id);
	}
	
}
