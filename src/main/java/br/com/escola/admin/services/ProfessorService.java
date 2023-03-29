package br.com.escola.admin.services;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Professor;
import br.com.escola.admin.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository repository;


    public List<Professor> obterProfessores(final Long id, final String nome, final String cpf){
        final Professor professor = new Professor(id,nome,cpf);
        ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();
        Example<Professor> example = Example.of(professor,caseInsensitiveExampleMatcher);
        return repository.findAll(example);
    }

    public Professor cadastrarProfessor(Professor professor) {
        if(findByCpfCadastro(professor.getCpf())!= null){
            throw new BusinessRuleException("Já existe professor com esse Cpf.");
        }
        return repository.save(professor);
    }

    public Professor atualizarProfessor(Long id, Professor professor) {
        Professor professorParaAtualizar = obterProfessorPorId(id);
        Professor professorPorCpf = findByCpf(professor.getCpf());
        if(professorPorCpf != null && !professorPorCpf.getId().equals(professorParaAtualizar.getId())){
            throw new BusinessRuleException("Já existe professor com esse CPF");
        }
        professorParaAtualizar.setCpf(professor.getCpf());
        professorParaAtualizar.setNome(professor.getNome());
        return repository.save(professorParaAtualizar);
    }

    public void deletarProfessor(Long id) {
        repository.delete(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não existe professor com esse ID")));
    }

    public Professor obterProfessorPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não existe professor com esse ID"));
    }

    public Professor findByCpf(String cpf) {
        Professor professor = repository.findByCpf(cpf);
        if(professor != null){
            return professor;
        } else {
            throw new ResourceNotFoundException("Professor não encontrado para o CPF: " + cpf);
        }
    }

    public Professor findByCpfCadastro(String cpf) {
            return repository.findByCpf(cpf);
    }


}
