package br.com.escola.admin.controllers;

import br.com.escola.admin.models.Diretor;
import br.com.escola.admin.services.DiretorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("diretores")
public class DiretorController {
    private final DiretorService service;

    public DiretorController(DiretorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Diretor> obterDiretores(){
        return service.obterDiretores();
    }

    @GetMapping("/{id}")
    public Diretor obterDiretorPorId(@PathVariable Long id){
        return service.obterDiretorPorId(id);
    }

    @PostMapping
    public Diretor criarDiretor(@RequestBody Diretor diretor){
        return service.criarDiretor(diretor);
    }

    @PutMapping("/{id}")
    public Diretor atulizarDiretor(@PathVariable Long id,@RequestBody Diretor diretor){
       return service.atulizarDiretorPorId(id,diretor);

    }

    @DeleteMapping("/{id}")
    public void deletarDiretor(@PathVariable Long id){
        service.removerDiretorPorId(id);
    }
}
