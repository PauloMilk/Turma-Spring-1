package br.com.escola.admin.services;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Professor;
import br.com.escola.admin.repositories.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {

    private final ProfessorRepository repository;

    public ProfessorService(ProfessorRepository repository) {
        this.repository = repository;
    }

    public Professor obterProfessorPorId(String id) {
        var professorOptional = repository.obterPorId(id);
        if (professorOptional.isEmpty()) {
            throw new ResourceNotFoundException("Professor não encontrado");
        }

        return professorOptional.get();
    }

    public List<Professor> obterProfessores() {
        return repository.obterProfessores();
    }

    public Professor salvarProfessor(Professor professor) {
        if (repository.existeProfessorComCpf(professor.getCpf())) {
            throw new BusinessRuleException("Já existe um professor com esse CPF");
        }
        repository.salvarProfessor(professor);
        return professor;
    }

    public Professor atualizarProfessor(String id, Professor professor) {
        var professorSalvo = obterProfessorPorId(id);

        var cpfIgual = professorSalvo.getCpf().equals(professor.getCpf());

        if (!cpfIgual && repository.existeProfessorComCpf(professor.getCpf())) {
            throw new BusinessRuleException("Já existe um professor com esse CPF");
        }

        professorSalvo.setNome(professor.getNome());
        professorSalvo.setCpf(professor.getCpf());
        professorSalvo.setEspecialidade(professor.getEspecialidade());

        repository.salvarProfessor(professorSalvo);
        return professorSalvo;
    }

    public void removerProfessor(String id) {
        var professorSalvo = obterProfessorPorId(id);
        repository.removerProfessor(professorSalvo);
    }
}
