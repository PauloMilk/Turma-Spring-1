package br.com.escola.admin.repositories;

import java.util.List;
import java.util.Optional;

import br.com.escola.admin.models.Diretor;

public interface DiretorRepository {

	Optional<Diretor> obter(Long id);
	List<Diretor> obterTodos();
	void salvar(Diretor diretor);
	void deletar(Diretor diretor);
	boolean existeComId(Long id);
	boolean existeComCpf(String cpf);
	
}
