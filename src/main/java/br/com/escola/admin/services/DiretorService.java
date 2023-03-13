package br.com.escola.admin.services;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Diretor;
import br.com.escola.admin.repositories.DiretorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiretorService {
    @Autowired
    private DiretorRepository repository;
    public List<Diretor> obterDiretores(){
        return repository.findAll();
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
        return repository.findByCpf(cpf);
    }
}
