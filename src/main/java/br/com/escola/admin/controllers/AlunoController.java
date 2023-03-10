package br.com.escola.admin.controllers;

import br.com.escola.admin.models.Aluno;
import br.com.escola.admin.services.AlunoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AlunoController {

    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @GetMapping("/alunos") //GET
    public List<Aluno> consultarAlunos() {
        System.out.println("Controller");
        return service.consultarAlunos();
    }


    @GetMapping("/alunos/{cpf}")
    public Aluno consultarAlunoPorCpf(@PathVariable String cpf) {
        Aluno aluno = service.consultarAlunoPorCpf(cpf);
        return aluno;
    }

    @PostMapping("/alunos")
    public Aluno criarAluno(@RequestBody Aluno aluno) {
        Aluno alunoSalvo = service.criarAluno(aluno);
        return alunoSalvo;
    }


    @PutMapping("/alunos/{cpf}")
    public Aluno atualizarAluno(@PathVariable String cpf, @RequestBody Aluno aluno) {
        //Buscando no meu banco de dados o aluno com o cpf informado
        var alunoAtualizado = service.atualizarAluno(cpf, aluno);

        return alunoAtualizado;
    }

    @DeleteMapping("/alunos/{cpf}")
    public void deletarAluno(@PathVariable String cpf) {
        service.removerAlunoPorCpf(cpf);
    }


}