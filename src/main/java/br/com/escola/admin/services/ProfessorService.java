package br.com.escola.admin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.escola.admin.models.Professor;
import br.com.escola.admin.repositories.ProfessorRepository;

@Service
public class ProfessorService {

	private final ProfessorRepository repository;

	public ProfessorService(ProfessorRepository repository) {
		this.repository = repository;
	}

	public List<Professor> obterTodos() {
		return repository.obterTodos();
	}

	public Professor obter(Long id) {
		Optional<Professor> professorSelecionado = repository.obter(id);
		
		if (professorSelecionado.isEmpty()) {
			throw new RuntimeException("Professor nao existe");
		}
		
		return professorSelecionado.get();
	}

	public Professor salvar(Professor professor) {
		if (repository.existeComId(professor.getId())) {
			throw new RuntimeException("Ja existe um professor com esse id");
		}
		
		if (repository.existeComCpf(professor.getCpf())) {
			throw new RuntimeException("Ja existe um professor com esse cpf");
		}
		
		repository.salvar(professor);
		return professor;
	}

	public Professor atualizar(Long id, Professor professor) {
		Professor professorSalvo = obter(id);
		
		professorSalvo.setId(id);
		professorSalvo.setNome(professor.getNome());
		professorSalvo.setEspecialidade(professor.getEspecialidade());
		
		return professorSalvo;
	}

	public void deletar(Long id) {
		Professor professorSalvo = obter(id);
		repository.deletar(professorSalvo);
	}
	
	
	
}
