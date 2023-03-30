package br.com.escola.admin.controllers;


import br.com.escola.admin.models.Professor;
import br.com.escola.admin.services.ProfessorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/professores")
public class ProfessorController {


    private final ProfessorService service;

    public ProfessorController(ProfessorService service) {
        this.service = service;
    }


    //TODO MUNDO
    @GetMapping
    public List<Professor> findAll() {
        return service.findAll();
    }


    //TODO MUNDO
    @GetMapping("/{id}")
    public Professor findById(@PathVariable UUID id) {
        return service.findById(id);
    }


    //DIRETOR
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Professor save(@RequestBody Professor professor) {
        professor.setId(UUID.randomUUID());
        return service.save(professor);
    }


    //DIRETOR
    @PutMapping("/{id}")
    public Professor update(@PathVariable UUID id, @RequestBody Professor professor) {
        professor.setId(id);
        return service.update(professor);
    }


    //DIRETOR
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
