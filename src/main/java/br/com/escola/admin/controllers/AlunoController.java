package br.com.escola.admin.controllers;

import br.com.escola.admin.models.Aluno;
import br.com.escola.admin.services.AlunoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("alunos")
public class AlunoController {

    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @GetMapping //GET
    public List<Aluno> consultarAlunos() {
        System.out.println("Controller");
        return service.consultarAlunos();
    }


    @GetMapping("/{id}")
    public Aluno consultarAlunoPorId(@PathVariable Long id) {
        return service.consultarAlunoPorId(id);

    }

    @PostMapping
    public Aluno criarAluno(@RequestBody Aluno aluno) {
        return service.criarAluno(aluno);
    }


    @PutMapping("/{id}")
    public Aluno atualizarAluno(@PathVariable Long id, @RequestBody Aluno aluno) {
        //Buscando no meu banco de dados o aluno com o cpf informado
        var alunoAtualizado = service.atualizarAluno(id, aluno);

        return alunoAtualizado;
    }

    @DeleteMapping("/{id}")
    public void deletarAluno(@PathVariable Long id) {
        service.removerAlunoPorId(id);
    }


}