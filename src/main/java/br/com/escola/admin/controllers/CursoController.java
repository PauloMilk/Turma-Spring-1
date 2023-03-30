package br.com.escola.admin.controllers;

import br.com.escola.admin.models.Curso;
import br.com.escola.admin.services.CursoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cursos")
public class CursoController {


    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }


    //TODO MUNDO
    @GetMapping
    public List<Curso> findAll() {
        return service.findAll();
    }

    //TODO MUNDO
    @GetMapping("/{id}")
    public Curso findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    //DIRETOR
    @PostMapping
    public Curso save(@RequestBody Curso curso) {
        curso.setId(UUID.randomUUID());
        return service.save(curso);
    }

    //DIRETOR
    @PutMapping("/{id}")
    public Curso update(@PathVariable UUID id, @RequestBody Curso curso) {
        curso.setId(id);
        return service.update(curso);
    }

    //DIRETOR
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }


    // /cursos/{id}/aluno/{id} -> POST -> Adicionar aluno no curso
    @PostMapping("/{id}/aluno/{cpfAluno}")
    public void addAlunoAoCurso(@PathVariable UUID id, @PathVariable String cpfAluno) {
        service.addAlunoAoCurso(id, cpfAluno);
    }

    @PatchMapping("/{idCurso}/aluno/{cpfAluno}/nota")
    public void adicionarNotaAoAlunoCurso(@PathVariable UUID idCurso, @PathVariable String cpfAluno, @RequestBody CursoNota nota) {
        service.addNotaAoAlunoCurso(idCurso, cpfAluno, nota.getNota());
    }

    @GetMapping("/{idCurso}/aluno/{cpfAluno}/nota")
    public CursoNota obterNotaDoCurso(@PathVariable UUID idCurso, @PathVariable String cpfAluno) {
        var nota = service.obterNotaDoCurso(idCurso, cpfAluno);

        return new CursoNota(nota);
    }


}


class CursoNota {
    private Double nota;


    public CursoNota(Double nota) {
        this.nota = nota;
    }

    public CursoNota() {
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }
}