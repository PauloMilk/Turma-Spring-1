package br.com.escola.admin.services;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Diretor;
import br.com.escola.admin.repositories.DiretorRepositoryEmMemoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiretorService {
    @Autowired
    private DiretorRepositoryEmMemoria repository;
    public List<Diretor> obterDiretores(){
        return repository.obterDiretores();
    }

    public Diretor cadastrarDiretor(Diretor diretor) {
        if(existeDiretorPorCpf(diretor.getCpf())){
            throw new BusinessRuleException("Já existe diretor com esse Cpf.");
        }
        return repository.salvarDiretor(diretor);
    }

    public Diretor atualizarDiretor(Long id, Diretor diretor) {
        Optional<Diretor> diretorOpt = repository.obterDiretorComCpf(diretor.getCpf());
        if(diretorOpt.isPresent() && diretorOpt.get().getId() !=  id){
            throw new BusinessRuleException("Já existe diretor com esse Cpf.");
        }
        Diretor diretorParaAtualizar = obterDiretorPorId(id);
        diretorParaAtualizar.setId(id);
        diretorParaAtualizar.setNome(diretor.getNome());
        diretorParaAtualizar.setCpf(diretor.getCpf());
        return diretorParaAtualizar;
    }

    public void deletarDiretor(Long id) {
        Diretor diretorParaAtualizar = obterDiretorPorId(id);
        repository.removerDiretor(diretorParaAtualizar);
    }

    public Diretor obterDiretorPorId(Long id) {
        Optional<Diretor> diretorOpt = repository.findById(id);
        if(diretorOpt.isEmpty()){
            throw new ResourceNotFoundException("Não existe diretor com esse ID");
        }
        return diretorOpt.get();
    }

    public boolean existeDiretorPorCpf(String cpf) {
        Optional<Diretor> diretorOpt = repository.obterDiretorComCpf(cpf);
        if(diretorOpt.isEmpty()){
            return false;
        }
        return true;
    }
}
