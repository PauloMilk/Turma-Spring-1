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

    @GetMapping
    public List<Professor> obterProfessores(){

        return service.obterProfessores();
    }

    @GetMapping("/{id}")
    public Professor obterProfessorPorId(@PathVariable Long id){

        return service.obterProfessorPorId(id);
    }

    @PostMapping
    public Professor criarProfessor(@RequestBody Professor professor){
        Professor professorSalvo = service.criarProfessor(professor);
        return professorSalvo;
    }

    @PutMapping("/{id}")
    public Professor atualizarProfessor(@PathVariable Long id, @RequestBody Professor professor){
        var professorAtulizado = service.atualizarProfessorPorId(id, professor);
        return professorAtulizado;
    }

    @DeleteMapping("/{id}")
    public void deletarProfessor(@PathVariable Long id){

        service.removerProfessorPorId(id);
    }
}
