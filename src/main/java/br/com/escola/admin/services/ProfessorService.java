package br.com.escola.admin.services;

import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Professor;
import br.com.escola.admin.repositories.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProfessorService {

    private final ProfessorRepository repository;

    public ProfessorService(ProfessorRepository repository) {
        this.repository = repository;
    }

    public List<Professor> findAll() {
        return repository.findAll();
    }

    public Professor findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor not found"));
    }

    public Professor save(Professor teacher) {
        return repository.save(teacher);
    }

    public Professor update(Professor professor) {
        return repository.save(professor);
    }

    public void delete(UUID id) {
        repository.delete(findById(id));
    }
}
