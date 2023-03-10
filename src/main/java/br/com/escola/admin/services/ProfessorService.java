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
		return repository.obterTodos();
	}

	public Professor obter(Long id) {
		Optional<Professor> professorSelecionado = repository.obter(id);
		
		if (professorSelecionado.isEmpty()) {
			var exception = new ResourceNotFoundException("Professor não encontrado");
			logger.debug(exception.getMessage());
			throw exception;
		}
		
		return professorSelecionado.get();
	}

	public Professor salvar(Professor professor) {
		if (repository.existeComId(professor.getId())) {
			var exception = new BusinessRuleException("Ja existe um professor com esse id");
			logger.error(exception.getMessage());
			throw exception;
		}
		
		if (repository.existeComCpf(professor.getCpf())) {
			var exception = new BusinessRuleException("Ja existe um professor com esse cpf");
			logger.error(exception.getMessage());
			throw exception;
		}
		
		repository.salvar(professor);
		return professor;
	}

	public Professor atualizar(Long id, Professor professor) {
		Professor professorSalvo = obter(id);
		
		professorSalvo.setNome(professor.getNome());
		professorSalvo.setEspecialidade(professor.getEspecialidade());
		
		return professorSalvo;
	}

	public void deletar(Long id) {
		Professor professorSalvo = obter(id);
		repository.deletar(professorSalvo);
	}
	
	
	
}
