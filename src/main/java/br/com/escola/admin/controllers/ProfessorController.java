package br.com.escola.admin.controllers;

import br.com.escola.admin.models.Professor;
import br.com.escola.admin.services.ProfessorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("professores")
public class ProfessorController {

    private final ProfessorService service;

    public ProfessorController(ProfessorService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Professor obterProfessorPorId(@PathVariable String id) {
        return service.obterProfessorPorId(id);
    }

    @GetMapping
    public List<Professor> obterProfessores() {
        return service.obterProfessores();
    }

    @PostMapping
    public Professor salvarProfessor(@RequestBody Professor professor) {
        return service.salvarProfessor(professor);
    }

    @PutMapping("/{id}")
    public Professor atualizarProfessor(@PathVariable String id, @RequestBody Professor professor) {
        return service.atualizarProfessor(id, professor);
    }

    @DeleteMapping("/{id}")
    public void removerProfessor(@PathVariable String id) {
        service.removerProfessor(id);
    }
}
