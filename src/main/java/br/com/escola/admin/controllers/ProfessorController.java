package br.com.escola.admin.controllers;

import br.com.escola.admin.models.Professor;
import br.com.escola.admin.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProfessorController {
    @Autowired
    private ProfessorService service;

    @GetMapping("/professores")
    public ResponseEntity<List<Professor>> consultarProfessores(@RequestParam (required = false) Long id, @RequestParam (required = false) String nome, @RequestParam (required = false) String cpf) {
        return ResponseEntity.ok().body(service.obterProfessores(id,nome,cpf));
    }


    @GetMapping("/professores/{id}")
    public ResponseEntity<Professor> consultarProfessorPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.obterProfessorPorId(id));
    }

    @PostMapping("/professores")
    public ResponseEntity<Professor> criarProfessor(@RequestBody Professor professor) {
        return ResponseEntity.created(null).body(service.cadastrarProfessor(professor));
    }


    @PutMapping("/professores/{id}")
    public ResponseEntity<Professor> atualizarProfessor(@PathVariable Long id, @RequestBody Professor professor) {
        return ResponseEntity.ok().body(service.atualizarProfessor(id, professor));
    }

    @DeleteMapping("/professores/{id}")
    public ResponseEntity<Void> deletarProfessor(@PathVariable Long id) {
        service.deletarProfessor(id);
        return ResponseEntity.noContent().build();
    }

}
