package br.com.escola.admin.repositories;

import br.com.escola.admin.models.Professor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DatabaseProfessorRepository implements ProfessorRepository {

    private final List<Professor> professores = new ArrayList<>(
            Arrays.asList(
                    new Professor("Roberto", "05931837035", "Português"),
                    new Professor("Elvira", "05931837036", "Matemática")
            )
    );

    @Override
    public Optional<Professor> obterPorId(String id) {
        return professores.stream()
                .filter(professor -> professor.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Professor> obterProfessores() {
        return professores;
    }

    @Override
    public void salvarProfessor(Professor professor) {
        var existeProfessor = existeProfessorComCpf(professor.getCpf());
        if (!existeProfessor) {
            professores.add(professor);
        }
    }

    @Override
    public void removerProfessor(Professor professor) {
        professores.remove(professor);
    }

    @Override
    public boolean existeProfessorComCpf(String cpf) {
        return obterProfessorPorCpf(cpf).isPresent();
    }

    private Optional<Professor> obterProfessorPorCpf(String cpf) {
        return professores.stream()
                .filter(professor -> professor.getCpf().equals(cpf))
                .findFirst();
    }
}
