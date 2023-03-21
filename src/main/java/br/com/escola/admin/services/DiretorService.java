package br.com.escola.admin.services;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Diretor;
import br.com.escola.admin.repositories.DiretorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiretorService {

	private final DiretorRepository repository;

	public DiretorService(DiretorRepository repository) {
		this.repository = repository;
	}

	public List<Diretor> obterTodos() {
		return repository.findAll();
	}

	public Diretor obter(Long id) {
		Optional<Diretor> diretorSelecionado = repository.findById(id);
		return diretorSelecionado.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Diretor criar(Diretor diretor) {
		if (repository.existsByCpf(diretor.getCpf())) {
			throw new BusinessRuleException("Já existe um diretor para esse cpf");
		}
		
		return repository.save(diretor);
	}

	public Diretor atualizar(Long id, Diretor diretor) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException(id);
		}

		diretor.setId(id);
		return repository.save(diretor);
	}

	public Void deletar(Long id) {
		Diretor diretorSalvo = obter(id);
		repository.delete(diretorSalvo);
		return null;
	}
	
}
