package br.com.escola.admin.services;

import br.com.escola.admin.models.Professor;
import br.com.escola.admin.repositories.ProfessorRepositoryEmMemoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepositoryEmMemoria repository;
    public List<Professor> obterProfessores(){
        return repository.obterProfessores();
    }

    public Professor cadastrarProfessor(Professor professor) {
        if(repository.existeProfessorComCpf(professor.getCpf())){
            throw new RuntimeException("Já existe professor com esse Cpf.");
        }
        return repository.salvarProfessor(professor);
    }

    public Professor atualizarProfessor(Long id, Professor professor) {

        if(repository.findById(id).isPresent()){
            Professor professorParaAtualizar = repository.findById(id).get();
            professorParaAtualizar.setId(id);
            professorParaAtualizar.setNome(professor.getNome());
            professorParaAtualizar.setEspecialidade(professor.getEspecialidade());
            professorParaAtualizar.setCpf(professor.getCpf());
            return professorParaAtualizar;
        } else {
            throw new RuntimeException("Não existe professor com esse id");
        }
}

    public void deletarProfessor(Long id) {
        Optional<Professor> professorOpt = repository.findById(id);
        if(professorOpt.isPresent()){
            repository.removerProfessor(professorOpt.get());
        } else {
            throw new RuntimeException("Não existe professor com esse id");
        }

    }

    public Professor obterProfessorPorId(Long id) {
        Optional<Professor> professorOpt = repository.findById(id);
        if(professorOpt.isEmpty()){
            throw new RuntimeException("Não existe professor com esse ID");
        }
        return professorOpt.get();
    }
}
