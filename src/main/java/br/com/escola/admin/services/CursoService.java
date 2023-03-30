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
import java.util.UUID;

@Service
public class CursoService {

    private final CursoRepository repository;

    private final AlunoRepository alunoRepository;
    private final CursoAlunoNotaRepository cursoAlunoNotaRepository;

    public CursoService(CursoRepository repository, AlunoRepository alunoRepository, CursoAlunoNotaRepository cursoAlunoNotaRepository) {
        this.repository = repository;
        this.alunoRepository = alunoRepository;
        this.cursoAlunoNotaRepository = cursoAlunoNotaRepository;
    }

    public List<Curso> findAll() {
        return repository.findAll();
    }

    public Curso findById(UUID id) {

        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Curso não encontrado")
        );
    }

    public Curso save(Curso curso) {
        return repository.save(curso);
    }

    public Curso update(Curso curso) {
        return repository.save(curso);
    }

    public void delete(UUID id) {
        repository.delete(findById(id));
    }

    public void addAlunoAoCurso(UUID id, String cpf) {
        Curso curso = findById(id);
        Aluno aluno = alunoRepository.obterAlunoPorCpf(cpf).orElseThrow(
                () -> new ResourceNotFoundException("Aluno não encontrado")
        );

        var cursoAlunoId = new CursoAlunoNotaID(aluno, curso);
        var cursoAlunoNota = new CursoAlunoNota(cursoAlunoId, 0.0);

        cursoAlunoNotaRepository.save(cursoAlunoNota);
    }

    public void addNotaAoAlunoCurso(UUID idCurso, String cpfAluno, Double nota) {
        Curso curso = findById(idCurso);
        Aluno aluno = alunoRepository.obterAlunoPorCpf(cpfAluno).orElseThrow(
                () -> new ResourceNotFoundException("Aluno não encontrado")
        );
        var cursoAlunoId = new CursoAlunoNotaID(aluno, curso);
        var alunoCursoNota = cursoAlunoNotaRepository.findById(cursoAlunoId).orElseThrow(
                () -> new ResourceNotFoundException("Vinculo de aluno e curso não encontrado")
        );
        alunoCursoNota.setNota(nota);
        cursoAlunoNotaRepository.save(alunoCursoNota);
    }

    public Double obterNotaDoCurso(UUID idCurso, String cpfAluno) {
        Curso curso = findById(idCurso);
        Aluno aluno = alunoRepository.obterAlunoPorCpf(cpfAluno).orElseThrow(
                () -> new ResourceNotFoundException("Aluno não encontrado")
        );
        var cursoAlunoId = new CursoAlunoNotaID(aluno, curso);
        var alunoCursoNota = cursoAlunoNotaRepository.findById(cursoAlunoId).orElseThrow(
                () -> new ResourceNotFoundException("Vinculo de aluno e curso não encontrado")
        );
        return alunoCursoNota.getNota();
    }
}
