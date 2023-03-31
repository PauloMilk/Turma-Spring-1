package br.com.escola.admin.controllers.curso;

import br.com.escola.admin.controllers.curso.dto.CursoCreateDto;
import br.com.escola.admin.controllers.curso.dto.CursoDto;
import br.com.escola.admin.controllers.curso.dto.CursoUpdateDto;
import br.com.escola.admin.controllers.curso.dto.NotaAlunoCursoDto;
import br.com.escola.admin.services.CursoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    // TODOS
    @GetMapping
    public ResponseEntity<List<CursoDto>> obterTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(
                service.obterTodos()
                        .stream()
                        .map(curso -> CursoDto.from(curso))
                        .toList()
        );
    }

    // TODOS
    @GetMapping(path = "/{id}")
    public ResponseEntity<CursoDto> obter(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                CursoDto.from(service.obter(id))
        );
    }

    // DIRETOR
    @PostMapping
    public ResponseEntity<CursoDto> salvar(@RequestBody @Valid CursoCreateDto cursoDto) {
        var curso = CursoCreateDto.toEntity(cursoDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                CursoDto.from(service.salvar(curso))
        );
    }

    // DIRETOR
    @PutMapping(path = "/{id}")
    public ResponseEntity<CursoDto> atualizar(@PathVariable Long id, @RequestBody @Valid
                CursoUpdateDto cursoDto) {

        var curso = CursoUpdateDto.toEntity(cursoDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                CursoDto.from(service.atualizar(id, curso))
        );
    }

    // DIRETOR
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/{id}/aluno/{idAluno}")
    public ResponseEntity<Object> addAlunoAoCurso(@PathVariable Long id, @PathVariable Long idAluno) {
        boolean adicionado = service.addAlunoAoCurso(id, idAluno);

        if (!adicionado) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Aluno já matriculado para o curso, Id= " + id);
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping(path = "/{idCurso}/aluno/{idAluno}/nota")
    public ResponseEntity<Void> atribuirNotaAoAlunoCurso(@PathVariable Long idCurso, @PathVariable Long idAluno,
                  @RequestBody NotaAlunoCursoDto notaAluno) {

        service.atribuirNotaAoAluno(idCurso, idAluno, notaAluno.nota().doubleValue());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(path = "/{idCurso}/aluno/{idAluno}/nota")
    public ResponseEntity<CursoNota> obterNotaDoCurso(@PathVariable Long idCurso, @PathVariable Long idAluno) {
        double nota = service.obterNotaDoCurso(idCurso, idAluno);
        return ResponseEntity.status(HttpStatus.OK).body(new CursoNota(nota));
    }

}

class CursoNota {

    private Double nota;

    public CursoNota(Double nota) {
        this.nota = nota;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

}
