package br.com.escola.admin.controllers.diretor;

import br.com.escola.admin.controllers.diretor.dtos.DiretorCreateDto;
import br.com.escola.admin.controllers.diretor.dtos.DiretorDto;
import br.com.escola.admin.controllers.diretor.dtos.DiretorUpdateDto;
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
	public ResponseEntity<List<DiretorDto>> obterTodos() {
		return ResponseEntity.status(HttpStatus.OK).body(
				service.obterTodos()
						.stream()
						.map(diretor -> DiretorDto.from(diretor))
						.toList()
		);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<DiretorDto> obter(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(
				DiretorDto.from(service.obter(id))
		);
	}
	
	@PostMapping
	public ResponseEntity<DiretorDto> criar(@RequestBody @Valid DiretorCreateDto diretorDto) {
		var diretor = DiretorCreateDto.toEntity(diretorDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(
				DiretorDto.from(service.criar(diretor))
		);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<DiretorDto> atualizar(@PathVariable Long id, @RequestBody @Valid
					DiretorUpdateDto diretorDto) {

		var diretor = DiretorUpdateDto.toEntity(diretorDto);

		return ResponseEntity.status(HttpStatus.OK).body(
				DiretorDto.from(service.atualizar(id, diretor))
		);
	}
	
	@DeleteMapping(path = "/{id}") 
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.deletar(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
}
