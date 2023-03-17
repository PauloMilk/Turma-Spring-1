package br.com.escola.admin.services;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Diretor;
import br.com.escola.admin.repositories.DiretorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiretorService {
    @Autowired
    private DiretorRepository repository;


    public List<Diretor> obterDiretores(final Long id, final String nome, final String cpf){
        final Diretor diretor = new Diretor(id,nome,cpf);
        ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();
        Example<Diretor> example = Example.of(diretor,caseInsensitiveExampleMatcher);
        return repository.findAll(example);
    }

    public Diretor cadastrarDiretor(Diretor diretor) {
        if(findByCpf(diretor.getCpf())!= null){
            throw new BusinessRuleException("Já existe diretor com esse Cpf.");
        }
        return repository.save(diretor);
    }

    public Diretor atualizarDiretor(Long id, Diretor diretor) {
        Diretor diretorParaAtualizar = obterDiretorPorId(id);
        Diretor diretorPorCpf = findByCpf(diretor.getCpf());
        if(diretorPorCpf != null && !diretorPorCpf.getId().equals(diretorParaAtualizar.getId())){
            throw new BusinessRuleException("Já existe diretor com esse CPF");
        }
        diretorParaAtualizar.setCpf(diretor.getCpf());
        diretorParaAtualizar.setNome(diretor.getNome());
        return repository.save(diretorParaAtualizar);
    }

    public void deletarDiretor(Long id) {
        repository.delete(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não existe diretor com esse ID")));
    }

    public Diretor obterDiretorPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não existe diretor com esse ID"));
    }

    public Diretor findByCpf(String cpf) {
        Diretor diretor = repository.findByCpf(cpf);
            if(diretor != null){
                return repository.findByCpf(cpf);
            } else {
                throw new ResourceNotFoundException("Diretor não encontrado para o CPF: " + cpf);
            }
    }
}
