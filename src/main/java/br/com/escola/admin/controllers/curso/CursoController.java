package br.com.escola.admin.controllers.curso;

import br.com.escola.admin.controllers.curso.dto.RelatorioNotasResponse;
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
    public ResponseEntity<List<RelatorioNotasResponse>> relatorio(@RequestParam(required = false) Long curso,
                                                                  @RequestParam(required = false) Long aluno){
        return ResponseEntity.ok().body(service.relatorioNotas(curso, aluno));
    }




}
