package br.com.escola.admin.repositories;

import br.com.escola.admin.models.Aluno;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Deprecated
public class EmMemoriaAlunoRepository {


    // InMemoryData
    private List<Aluno> alunos = new ArrayList<>(
            Arrays.asList(
                    new Aluno("João", "12345678900"),
                    new Aluno("Maria", "12345678901"),
                    new Aluno("José", "12345678902")
            )
    );

    public Optional<Aluno> obterAlunoPorCpf(String cpf) {
        return alunos.stream().filter(
                a -> a.getCpf().equals(cpf)
        ).findFirst();
    }

    public List<Aluno> obterAlunos() {
        System.out.println("Repository");
        return alunos;
    }

    public void salvarAluno(Aluno aluno) {
        var alunoOptional = obterAlunoPorCpf(aluno.getCpf());

        if (alunoOptional.isPresent()) {
            alunoOptional.get().setNome(aluno.getNome());
            alunoOptional.get().setCpf(aluno.getCpf());
        } else {
            alunos.add(aluno);
        }
    }

    public boolean existeAlunoComCpf(String cpf) {
        return obterAlunoPorCpf(cpf).isPresent();
    }

    public void removerAluno(Aluno aluno) {
        alunos.remove(aluno);
    }
}
