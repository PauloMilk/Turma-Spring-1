package br.com.escola.admin.repositories;

import br.com.escola.admin.models.Aluno;

import java.util.List;
import java.util.Optional;

//ABSTRAÇÃO
public interface AlunoRepository {


    Optional<Aluno> obterAlunoPorCpf(String cpf);

    List<Aluno> obterAlunos();

    void salvarAluno(Aluno aluno);

    boolean existeAlunoComCpf(String cpf);

    void removerAluno(Aluno aluno);
}
