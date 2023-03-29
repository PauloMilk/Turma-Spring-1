package br.com.escola.admin.controllers;

import br.com.escola.admin.controllers.DTO.RelatorioNotas;
import br.com.escola.admin.models.Curso;
import br.com.escola.admin.models.CursoAlunoNota;
import br.com.escola.admin.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    CursoService service;
    @PostMapping
    public ResponseEntity<Curso> criarCurso(@RequestBody Curso curso) {
        return ResponseEntity.created(null).body(service.cadastrarCurso(curso));
    }

    @PostMapping("/{idCurso}/{idAluno}/{nota}")
    public ResponseEntity<CursoAlunoNota> cadastrarNota(
            @PathVariable Long idCurso,
            @PathVariable Long idAluno,
            @PathVariable Double nota) {
        return ResponseEntity.created(null).body(service.cadastrarNota(idCurso, idAluno, nota));
    }

    @GetMapping("/relatorio")
    public ResponseEntity<List<RelatorioNotas>> relatorio(){
        return ResponseEntity.ok().body(service.relatorioNotas());
    }




}
