package br.com.escola.admin.controllers.aluno;

import br.com.escola.admin.controllers.aluno.dtos.AlunoCreateDto;
import br.com.escola.admin.controllers.aluno.dtos.AlunoDto;
import br.com.escola.admin.controllers.aluno.dtos.AlunoUpdateDto;
import br.com.escola.admin.models.Aluno;
import br.com.escola.admin.services.AlunoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AlunoDto>> obterTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(
            service.obterTodos()
                    .stream()
                    .map(aluno -> AlunoDto.from(aluno))
                    .toList()
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AlunoDto> obterPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                AlunoDto.from(service.obterPorId(id))
        );
    }

    @PostMapping
    public ResponseEntity<AlunoDto> salvar(@RequestBody @Valid AlunoCreateDto alunoDto) {
        var aluno = AlunoCreateDto.toEntity(alunoDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                AlunoDto.from(service.salvar(aluno))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoDto> atualizar(@PathVariable Long id, @RequestBody @Valid
                    AlunoUpdateDto alunoDto) {

        var aluno = AlunoUpdateDto.toEntity(alunoDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                AlunoDto.from(service.atualizar(id, aluno))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}