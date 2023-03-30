package br.com.escola.admin.controllers.professor;

import br.com.escola.admin.controllers.professor.dtos.ProfessorCreateDto;
import br.com.escola.admin.controllers.professor.dtos.ProfessorDto;
import br.com.escola.admin.controllers.professor.dtos.ProfessorUpdateDto;
import br.com.escola.admin.services.ProfessorService;
import jakarta.validation.Valid;
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
	public ResponseEntity<List<ProfessorDto>> obterTodos() {
		return ResponseEntity.status(HttpStatus.OK).body(
				service.obterTodos()
						.stream()
						.map(professor -> ProfessorDto.from(professor))
						.toList()
		);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<ProfessorDto> obter(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(
				ProfessorDto.from(service.obter(id))
		);
	}
	
	@PostMapping
	public ResponseEntity<ProfessorDto> salvar(@RequestBody @Valid ProfessorCreateDto professorDto) {
		var professor = ProfessorCreateDto.toEntity(professorDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(
				ProfessorDto.from(service.salvar(professor))
		);
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<ProfessorDto> atualizar(@PathVariable Long id, @RequestBody @Valid
				ProfessorUpdateDto professorDto) {

		var professor = ProfessorUpdateDto.toEntity(professorDto);

		return ResponseEntity.status(HttpStatus.OK).body(
				ProfessorDto.from(service.atualizar(id, professor))
		);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		service.deletar(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
}
