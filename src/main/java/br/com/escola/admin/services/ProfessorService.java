package br.com.escola.admin.services;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Professor;
import br.com.escola.admin.repositories.ProfessorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    private final Logger logger = LoggerFactory.getLogger(ProfessorService.class);
    private final ProfessorRepository repository;

    public ProfessorService(ProfessorRepository repository) {
        this.repository = repository;
    }


    public List<Professor> obterProfessores() {
        return repository.findAll();
    }

    public Professor obterProfessorPorId(Long id) {
        Optional<Professor> professorOptional = repository.findById(id);
        if (professorOptional.isEmpty()) {
            var exception = new ResourceNotFoundException("Professor não encontrado");
            logger.debug(exception.getMessage());
            throw exception;
        }
        return professorOptional.get();
    }

    public Professor criarProfessor(Professor professor) {
        Optional<Professor> existeProfessorComEsseCpf = repository.findByCpf(professor.getCpf());
        if (existeProfessorComEsseCpf.isPresent()) {
            var exception = new BusinessRuleException("Já existe um professor com esse cpf");
            logger.error(exception.getMessage());
            throw exception;

        }
        repository.save(professor);
        return professor;
    }

    public Professor atualizarProfessorPorId(Long id, Professor professor) {
        var professorSalvo = obterProfessorPorId(id);
        Optional<Professor> existeProfessorComEsseCpf = repository.findByCpf(professor.getCpf());
        if (existeProfessorComEsseCpf.isPresent()) {
            throw new BusinessRuleException("Já existe um professor com esse cpf");
        }
        professorSalvo.setNome(professor.getNome());
        professorSalvo.setCpf(professor.getCpf());
        professorSalvo.setEspecialidade(professor.getEspecialidade());
        repository.save(professorSalvo);
        return professorSalvo;
    }

    public void removerProfessorPorId(Long id) {
        Professor professor = obterProfessorPorId(id);

        repository.delete(professor);
    }

}
