package br.com.escola.admin.controllers.aluno;

import br.com.escola.admin.controllers.aluno.dto.AlunoCreateDTO;
import br.com.escola.admin.controllers.aluno.dto.AlunoDTO;
import br.com.escola.admin.models.Aluno;
import br.com.escola.admin.services.AlunoService;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.escola.admin.utils.validators.CPFValidator.isNotValid;

@RestController
public class AlunoController {

    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    //TODO MUNDO
    @GetMapping("/alunos") //GET
    public List<AlunoDTO> consultarAlunos() {
        return service.consultarAlunos()
                .stream()
                .map(AlunoDTO::from)
                .toList();
    }


    //TODO MUNDO
    @GetMapping("/alunos/{cpf}")
    public AlunoDTO consultarAlunoPorCpf(@PathVariable String cpf) {
        if (isNotValid(cpf)) {
            throw new IllegalArgumentException("CPF inválido");
        }
        Aluno aluno = service.consultarAlunoPorCpf(cpf);
        return AlunoDTO.from(aluno);
    }

    //DIRETOR
    @PostMapping("/alunos")
    @ResponseStatus(code = HttpStatus.CREATED)
    public AlunoDTO criarAluno(@RequestBody @Valid AlunoCreateDTO dto) {
        Aluno alunoSalvo = service.criarAluno(dto.toEntity());
        return AlunoDTO.from(alunoSalvo);
    }


    //DIRETOR
    @PutMapping("/alunos/{cpf}")
    public AlunoDTO atualizarAluno(@PathVariable String cpf, @RequestBody Aluno aluno) {
        //Buscando no meu banco de dados o aluno com o cpf informado
        var alunoAtualizado = service.atualizarAluno(cpf, aluno);

        return AlunoDTO.from(alunoAtualizado);
    }

    //DIRETOR
    @DeleteMapping("/alunos/{cpf}")
    public void deletarAluno(@PathVariable String cpf) {
        service.removerAlunoPorCpf(cpf);
    }


}