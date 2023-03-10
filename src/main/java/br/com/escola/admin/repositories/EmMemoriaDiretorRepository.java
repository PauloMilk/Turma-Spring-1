package br.com.escola.admin.repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import br.com.escola.admin.models.Diretor;

public class EmMemoriaDiretorRepository implements DiretorRepository {
	
	private List<Diretor> diretores = new ArrayList<>(
			Arrays.asList(
					new Diretor(1L, "Angela", "70826057810"),
					new Diretor(2L, "Gustavo", "26281971892")
			)
	);
	
	@Override
	public Optional<Diretor> obter(Long id) {
		return diretores.stream()
				.filter(diretor -> diretor.getId().equals(id))
				.findFirst();
	}

	@Override
	public List<Diretor> obterTodos() {
		return diretores;
	}

	@Override
	public void salvar(Diretor diretor) {
		diretores.add(diretor);
	}

	@Override
	public void deletar(Diretor diretor) {
		diretores.remove(diretor);
	}

	@Override
	public boolean existeComId(Long id) {
		return obter(id).isPresent();
	}

	@Override
	public boolean existeComCpf(String cpf) {
		return diretores.stream()
				.filter(diretor -> diretor.getCpf().equals(cpf))
				.findFirst()
				.isPresent();
	}

}
