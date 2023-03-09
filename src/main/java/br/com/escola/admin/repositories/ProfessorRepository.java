package br.com.escola.admin.repositories;

import br.com.escola.admin.models.Professor;

import java.util.List;
import java.util.Optional;

public interface ProfessorRepository {


    Optional<Professor> obterProfessorPorCpf(String cpf);

    List<Professor> obterProfessores();

    Professor salvarProfessor(Professor professor);

    boolean existeProfessorComCpf(String cpf);

    void removerProfessor(Professor professor);
}
