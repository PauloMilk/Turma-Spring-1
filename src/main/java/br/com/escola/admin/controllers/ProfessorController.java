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
    private ResponseEntity<List<Professor>> obterProfessores(){
        return ResponseEntity.ok().body(service.obterProfessores());
    }

    @GetMapping("/professores/{id}")
    private ResponseEntity<?> obterProfessorPorId(@PathVariable Long id){
        try {
            return ResponseEntity.ok().body(service.obterProfessorPorId(id));
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/professores")
    private ResponseEntity<?> cadastrarProfessor(@RequestBody Professor professor){
        try {
            return ResponseEntity.ok().body(service.cadastrarProfessor(professor));
        }
        catch (RuntimeException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

    }

    @PutMapping("/professores/{id}")
    private ResponseEntity<?> cadastrarProfessor(@PathVariable Long id, @RequestBody Professor professor){
        try {
            return ResponseEntity.ok().body(service.atualizarProfessor(id, professor));
        }
        catch (RuntimeException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/professores/{id}")
    private ResponseEntity<?> deletarProfessor(@PathVariable Long id){
        try {
            service.deletarProfessor(id);
            return ResponseEntity.ok().body("Professor deletado com sucesso");
        }
        catch (RuntimeException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
