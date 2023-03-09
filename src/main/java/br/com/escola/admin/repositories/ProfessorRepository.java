package br.com.escola.admin.repositories;

import br.com.escola.admin.models.Professor;

import java.util.List;
import java.util.Optional;

public interface ProfessorRepository {

    Optional<Professor> obterPorId(String id);

    List<Professor> obterProfessores();

    void salvarProfessor(Professor professor);

    void removerProfessor(Professor professor);

    boolean existeProfessorComCpf(String cpf);
}
