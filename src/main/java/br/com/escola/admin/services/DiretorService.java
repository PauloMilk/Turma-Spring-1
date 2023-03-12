package br.com.escola.admin.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Diretor;
import br.com.escola.admin.repositories.DiretorRepository;

@Service
public class DiretorService {

	private final DiretorRepository repository;
	private final Logger logger = LoggerFactory.getLogger(DiretorService.class);
	
	public DiretorService(DiretorRepository repository) {
		this.repository = repository;
	}

	public List<Diretor> obterTodos() {
		return repository.findAll();
	}

	public Diretor obter(Long id) {
		Optional<Diretor> diretorSelecionado = repository.findById(id);
		
		if (diretorSelecionado.isEmpty()) {
			var exception = new ResourceNotFoundException(id);
			logger.debug(exception.getMessage());
			throw exception;
		}
		
		return diretorSelecionado.get();
	}

	public Diretor criar(Diretor diretor) {
		if (repository.existsByCpf(diretor.getCpf())) {
			var exception = new BusinessRuleException("Já existe um diretor para esse cpf");
			logger.error(exception.getMessage());
			throw exception;
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

	public void deletar(Long id) {
		Diretor diretorSalvo = obter(id);
		repository.delete(diretorSalvo);
	}
	
}
