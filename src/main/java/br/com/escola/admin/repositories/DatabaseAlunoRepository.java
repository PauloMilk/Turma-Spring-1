package br.com.escola.admin.repositories;

import br.com.escola.admin.models.Aluno;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DatabaseAlunoRepository implements AlunoRepository {

    private final JpaAlunoRepository jpa;

    public DatabaseAlunoRepository(JpaAlunoRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Optional<Aluno> obterAlunoPorCpf(String cpf) {
        return jpa.findById(cpf);
    }

    @Override
    public List<Aluno> obterAlunos() {
        return jpa.findAll();
    }

    @Override
    public void salvarAluno(Aluno aluno) {
        jpa.save(aluno);
    }

    @Override
    public boolean existeAlunoComCpf(String cpf) {
        return jpa.existsById(cpf);
    }

    @Override
    public void removerAluno(Aluno aluno) {
        jpa.delete(aluno);
    }

    @Override
    public Optional<Aluno> obterAlunoPorNome(String nome) {
        return jpa.obterAlunoPorNome(nome);
    }
}