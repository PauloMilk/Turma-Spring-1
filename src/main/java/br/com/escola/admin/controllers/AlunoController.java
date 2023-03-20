package br.com.escola.admin.controllers;

import br.com.escola.admin.dtos.AlunoDto;
import br.com.escola.admin.models.Aluno;
import br.com.escola.admin.services.AlunoService;
import br.com.escola.admin.utils.validators.CPFValidator;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Parameter;
import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Aluno>> obterTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(service.obterTodos());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Aluno> obterPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.obterPorId(id));
    }

    @PostMapping
    public ResponseEntity<Aluno> salvar(@RequestBody @Valid AlunoDto alunoDto) {
        var aluno = new Aluno();
        BeanUtils.copyProperties(alunoDto, aluno);

        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(aluno));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(@PathVariable Long id, @RequestBody @Valid AlunoDto alunoDto) {
        var aluno = new Aluno();
        BeanUtils.copyProperties(alunoDto, aluno);

        return ResponseEntity.status(HttpStatus.OK).body(service.atualizar(id, aluno));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}