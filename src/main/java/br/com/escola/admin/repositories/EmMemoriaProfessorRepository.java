package br.com.escola.admin.repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import br.com.escola.admin.models.Professor;

public class EmMemoriaProfessorRepository implements ProfessorRepository {
	
	private List<Professor> professores = new ArrayList<>(
			Arrays.asList(
					new Professor(1L, "Marcos", "69278811823", "Engenharia de Produção"),
					new Professor(2L, "Luis", "49593134808", "Engenharia Eletrica"),
					new Professor(3L, "Marcelo", "20306517884", "Engenharia de Software")
			)
	);

	@Override
	public Optional<Professor> obter(Long id) {
		return professores.stream()
			.filter(p -> p.getId().equals(id))
			.findFirst();
	}

	@Override
	public List<Professor> obterTodos() {
		return professores;
	}

	@Override
	public void salvar(Professor professor) {
		professores.add(professor);
	}

	@Override
	public void deletar(Professor professor) {
		professores.remove(professor);
	}

	@Override
	public boolean existeComId(Long id) {
		return obter(id).isPresent();
	}

	@Override
	public boolean existeComCpf(String cpf) {
		return professores.stream()
				.filter(p -> p.getCpf().equals(cpf))
				.findFirst()
				.isPresent();
	}

}
