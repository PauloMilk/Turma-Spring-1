package br.com.escola.admin.services;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Professor;
import br.com.escola.admin.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository repository;
    public List<Professor> obterProfessores(){
        return repository.findAll();
    }

    public Professor cadastrarProfessor(Professor professor) {
        if(findByCpf(professor.getCpf())!= null){
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
        professorParaAtualizar.setEspecialidade(professor.getEspecialidade());
        return repository.save(professorParaAtualizar);
    }

    public void deletarProfessor(Long id) {
        repository.delete(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não existe professor com esse ID")));
    }

    public Professor obterProfessorPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não existe professor com esse ID"));
    }

    public Professor findByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }
}
