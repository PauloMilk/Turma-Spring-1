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
		return repository.obterTodos();
	}

	public Diretor obter(Long id) {
		Optional<Diretor> diretorSelecionado = repository.obter(id);
		
		if (diretorSelecionado.isEmpty()) {
			var exception = new ResourceNotFoundException("Diretor não encontrado");
			logger.debug(exception.getMessage());
			throw exception;
		}
		
		return diretorSelecionado.get();
	}

	public Diretor criar(Diretor diretor) {
		if (repository.existeComId(diretor.getId())) {
			var exception = new BusinessRuleException("Já existe um diretor para esse id");
			logger.error(exception.getMessage());
			throw exception;
		}
		
		if (repository.existeComCpf(diretor.getCpf())) {
			var exception = new BusinessRuleException("Já existe um diretor para esse cpf");
			logger.error(exception.getMessage());
			throw exception;
		}
		
		repository.salvar(diretor);
		return diretor;
	}

	public Diretor atualizar(Long id, Diretor diretor) {
		Diretor diretorSalvo = obter(id);
		
		diretorSalvo.setNome(diretor.getNome());
		diretorSalvo.setCpf(diretor.getCpf());
		
		return diretorSalvo;
	}

	public void deletar(Long id) {
		Diretor diretorSalvo = obter(id);
		repository.deletar(diretorSalvo);
	}
	
	
}
