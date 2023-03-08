package br.com.escola.admin.repositories;

import java.util.List;
import java.util.Optional;

import br.com.escola.admin.models.Professor;

public interface ProfessorRepository {

	Optional<Professor> obter(Long id);
	List<Professor> obterTodos();
	void salvar(Professor professor);
	void deletar(Professor professor);
	boolean existeComId(Long id);
	boolean existeComCpf(String cpf);
	
}
