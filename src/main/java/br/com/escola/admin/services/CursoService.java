package br.com.escola.admin.services;

import br.com.escola.admin.controllers.curso.dto.RelatorioNotasResponse;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Curso;
import br.com.escola.admin.models.CursoAlunoNota;
import br.com.escola.admin.models.CursoAlunoNotaId;
import br.com.escola.admin.repositories.AlunoRepository;
import br.com.escola.admin.repositories.CursoAlunoNotaRepository;
import br.com.escola.admin.repositories.CursoRepositoy;
import br.com.escola.admin.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    CursoRepositoy repository;

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    AlunoRepository alunoRepository;

    @Autowired
    CursoRepositoy cursoRepository;

    @Autowired
    CursoAlunoNotaRepository cursoAlunoNotaRepository;


    public Curso cadastrarCurso(Curso curso) {
        if(professorRepository.existsById(curso.getProfessor().getId())){
            return repository.save(curso);
        } else {
            throw new ResourceNotFoundException("Professor inexistente");
        }

    }

    public CursoAlunoNota cadastrarNota(Long idCurso, Long idAluno, Double nota) {
        CursoAlunoNotaId cursoAlunoNotaId = new CursoAlunoNotaId(idAluno,idCurso);
        return cursoAlunoNotaRepository.save(new CursoAlunoNota(cursoAlunoNotaId, nota));
    }


    public List<RelatorioNotasResponse> relatorioNotas(Long idCurso, Long idAluno) {
        return cursoAlunoNotaRepository.gerarRelatorio(idCurso, idAluno);

    }
}
