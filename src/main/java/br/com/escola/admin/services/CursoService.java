package br.com.escola.admin.services;

import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Aluno;
import br.com.escola.admin.models.Curso;
import br.com.escola.admin.models.CursoAlunoNota;
import br.com.escola.admin.models.CursoAlunoNotaID;
import br.com.escola.admin.repositories.AlunoRepository;
import br.com.escola.admin.repositories.CursoAlunoNotaRepository;
import br.com.escola.admin.repositories.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    private final CursoRepository repository;
    private final AlunoRepository alunoRepository;
    private final CursoAlunoNotaRepository cursoAlunoNotaRepository;

    public CursoService(CursoRepository repository, AlunoRepository alunoRepository,
                    CursoAlunoNotaRepository cursoAlunoNotaRepository) {

        this.repository = repository;
        this.alunoRepository = alunoRepository;
        this.cursoAlunoNotaRepository = cursoAlunoNotaRepository;
    }

    public List<Curso> obterTodos() {
        return repository.findAll();
    }

    public Curso obter(Long id) {
        Optional<Curso> optionalCurso = repository.findById(id);
        return optionalCurso.orElseThrow(() -> new ResourceNotFoundException(Curso.class, id));
    }

    public Curso salvar(Curso curso) {
        return repository.save(curso);
    }

    public Curso atualizar(Long id, Curso curso) {
        Curso cursoParaAtualizar = obter(id);

        cursoParaAtualizar.setNome(curso.getNome());
        cursoParaAtualizar.setDescricao(curso.getDescricao());
        cursoParaAtualizar.setUrlImagem(curso.getUrlImagem());
        return repository.save(cursoParaAtualizar);
    }

    public void deletar(Long id) {
        Curso curosSalvo = obter(id);
        repository.delete(curosSalvo);
    }

    public boolean addAlunoAoCurso(Long idCurso, Long idAluno) {
        var cursoAlunoNotaID = getCursoAlunoNotaID(idCurso, idAluno);
        boolean estaMatriculado = cursoAlunoNotaRepository.existsById(cursoAlunoNotaID);

        if (!estaMatriculado) {
            cursoAlunoNotaRepository.save(new CursoAlunoNota(cursoAlunoNotaID, 0));
            return true;
        }

        return false;
    }

    public void atribuirNotaAoAluno(Long idCurso, Long idAluno, Integer nota) {
        var cursoAlunoNotaID = getCursoAlunoNotaID(idCurso, idAluno);

        var cursoAlunoNota = cursoAlunoNotaRepository.findById(cursoAlunoNotaID)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Vinculo de aluno e curso não encontrado")
                );

        cursoAlunoNota.setNota(nota);
        cursoAlunoNotaRepository.save(cursoAlunoNota);
    }

    public double obterNotaDoCurso(Long idCurso, Long idAluno) {
        var cursoAlunoNotaID = getCursoAlunoNotaID(idCurso, idAluno);

        var cursoAlunoNota = cursoAlunoNotaRepository.findById(cursoAlunoNotaID)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Vinculo de aluno e curso não encontrado")
                );

        return cursoAlunoNota.getNota();
    }

    private CursoAlunoNotaID getCursoAlunoNotaID(Long idCurso, Long idAluno) {
        Curso curso = obter(idCurso);

        Aluno aluno = alunoRepository
                .findById(idAluno)
                .orElseThrow(() -> new ResourceNotFoundException(Aluno.class, idAluno));

        return new CursoAlunoNotaID(aluno, curso);
    }

}
