package br.com.escola.admin.controllers;

import br.com.escola.admin.models.Diretor;
import br.com.escola.admin.services.DiretorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DiretorController {
    @Autowired
    private DiretorService service;

    @GetMapping("/diretores")
    public ResponseEntity<List<Diretor>> consultarDiretores() {
        return ResponseEntity.ok().body(service.obterDiretores());
    }


    @GetMapping("/diretores/{id}")
    public ResponseEntity<Diretor> consultarDiretorPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.obterDiretorPorId(id));
    }

    @PostMapping("/diretores")
    public ResponseEntity<Diretor> criarDiretor(@RequestBody Diretor diretor) {
        return ResponseEntity.created(null).body(service.cadastrarDiretor(diretor));
    }


    @PutMapping("/diretores/{id}")
    public ResponseEntity<Diretor> atualizarDiretor(@PathVariable Long id, @RequestBody Diretor diretor) {
        return ResponseEntity.ok().body(service.atualizarDiretor(id, diretor));
    }

    @DeleteMapping("/diretores/{id}")
    public ResponseEntity<Void> deletarDiretor(@PathVariable Long id) {
        service.deletarDiretor(id);
        return ResponseEntity.noContent().build();
    }

}
