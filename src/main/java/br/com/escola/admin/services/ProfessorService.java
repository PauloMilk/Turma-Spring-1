package br.com.escola.admin.services;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Professor;
import br.com.escola.admin.repositories.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

	private final ProfessorRepository repository;

	public ProfessorService(ProfessorRepository repository) {
		this.repository = repository;
	}

	public List<Professor> obterTodos() {
		return repository.findAll();
	}

	public Professor obter(Long id) {
		Optional<Professor> professorSelecionado = repository.findById(id);
		return professorSelecionado.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Professor salvar(Professor professor) {
		if (repository.existsByCpf(professor.getCpf())) {
			throw new BusinessRuleException("Ja existe um professor com esse cpf");
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

	public Void deletar(Long id) {
		Professor professorSalvo = obter(id);
		repository.delete(professorSalvo);
		return null;
	}
	
}
