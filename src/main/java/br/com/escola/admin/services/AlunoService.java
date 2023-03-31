package br.com.escola.admin.services;

import br.com.escola.admin.controllers.aluno.dto.AlunoPostRequest;
import br.com.escola.admin.controllers.aluno.mapper.AlunoMapper;
import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Aluno;
import br.com.escola.admin.repositories.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository repository;


    public List<Aluno> obterAlunoes(final Long id, final String nome, final String cpf){
        final Aluno aluno = new Aluno(id,nome,cpf);
        ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();
        Example<Aluno> example = Example.of(aluno,caseInsensitiveExampleMatcher);
        return repository.findAll(example);
    }

    public Aluno cadastrarAluno(AlunoPostRequest alunoRequest) {
        Aluno aluno = AlunoMapper.INSTANCE.alunoRequestToAluno(alunoRequest);
        return repository.save(aluno);
    }

    public Aluno atualizarAluno(Long id, Aluno aluno) {
        Aluno alunoParaAtualizar = obterAlunoPorId(id);
        Aluno alunoPorCpf = findByCpf(aluno.getCpf());
        if(alunoPorCpf != null && !alunoPorCpf.getId().equals(alunoParaAtualizar.getId())){
            throw new BusinessRuleException("Já existe aluno com esse CPF");
        }
        alunoParaAtualizar.setCpf(aluno.getCpf());
        alunoParaAtualizar.setNome(aluno.getNome());
        return repository.save(alunoParaAtualizar);
    }

    public void deletarAluno(Long id) {
        repository.delete(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não existe aluno com esse ID")));
    }

    public Aluno obterAlunoPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não existe aluno com esse ID"));
    }

    public Aluno findByCpf(String cpf) {
        Aluno aluno = repository.findByCpf(cpf);
        if(aluno != null){
            return repository.findByCpf(cpf);
        } else {
            throw new ResourceNotFoundException("Aluno não encontrado para o CPF: " + cpf);
        }
    }

    public Aluno findByCpfCadastro(String cpf) {
            return repository.findByCpf(cpf);
    }
}
