package br.com.escola.admin.controllers;

import br.com.escola.admin.models.Diretor;
import br.com.escola.admin.services.DiretorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;



////DIRETOR
@RestController
@RequestMapping(value = "/diretores")
public class DiretorController {


    private final DiretorService service;

    public DiretorController(DiretorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Diretor>> findAll() {
        List<Diretor> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Diretor> findById(@PathVariable UUID id) {
        var diretor = service.findById(id);
        return ResponseEntity.ok().body(diretor);
    }

    @PostMapping
    public ResponseEntity<Diretor> insert(@RequestBody Diretor diretor) {
        var diretorSalvo = service.create(diretor);
        return ResponseEntity.status(201).body(diretorSalvo);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Diretor> update(@PathVariable UUID id, @RequestBody Diretor diretor) {
        diretor.setId(id);
        var diretorAtualizado = service.update(diretor);

        return ResponseEntity.ok().body(diretorAtualizado);
    }

}