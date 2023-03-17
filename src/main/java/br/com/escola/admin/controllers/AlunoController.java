package br.com.escola.admin.controllers;

import br.com.escola.admin.models.Aluno;
import br.com.escola.admin.services.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AlunoController {
    @Autowired
    private AlunoService service;

    @GetMapping("/alunos")
    public ResponseEntity<List<Aluno>> consultarAlunoes(@RequestParam (required = false) Long id, @RequestParam (required = false) String nome, @RequestParam (required = false) String cpf) {
        return ResponseEntity.ok().body(service.obterAlunoes(id,nome,cpf));
    }


    @GetMapping("/alunos/{id}")
    public ResponseEntity<Aluno> consultarAlunoPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.obterAlunoPorId(id));
    }

    @PostMapping("/alunos")
    public ResponseEntity<Aluno> criarAluno(@RequestBody Aluno aluno) {
        return ResponseEntity.created(null).body(service.cadastrarAluno(aluno));
    }


    @PutMapping("/alunos/{id}")
    public ResponseEntity<Aluno> atualizarAluno(@PathVariable Long id, @RequestBody Aluno aluno) {
        return ResponseEntity.ok().body(service.atualizarAluno(id, aluno));
    }

    @DeleteMapping("/alunos/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        service.deletarAluno(id);
        return ResponseEntity.noContent().build();
    }

}
