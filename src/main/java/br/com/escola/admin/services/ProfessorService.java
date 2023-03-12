package br.com.escola.admin.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Professor;
import br.com.escola.admin.repositories.ProfessorRepository;

@Service
public class ProfessorService {

	private final ProfessorRepository repository;
	private final Logger logger = LoggerFactory.getLogger(ProfessorService.class);

	public ProfessorService(ProfessorRepository repository) {
		this.repository = repository;
	}

	public List<Professor> obterTodos() {
		return repository.findAll();
	}

	public Professor obter(Long id) {
		Optional<Professor> professorSelecionado = repository.findById(id);
		
		if (professorSelecionado.isEmpty()) {
			var exception = new ResourceNotFoundException(id);
			logger.debug(exception.getMessage());
			throw exception;
		}
		
		return professorSelecionado.get();
	}

	public Professor salvar(Professor professor) {
		if (repository.existsByCpf(professor.getCpf())) {
			var exception = new BusinessRuleException("Ja existe um professor com esse cpf");
			logger.error(exception.getMessage());
			throw exception;
		}
		
		return repository.save(professor);
	}

	public Professor atualizar(Long id, Professor professor) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException(id);
		}
		
		professor.setId(id);
		return repository.save(professor);
	}

	public void deletar(Long id) {
		Professor professorSalvo = obter(id);
		repository.delete(professorSalvo);
	}
	
}
