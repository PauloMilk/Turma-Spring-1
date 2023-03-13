package br.com.escola.admin.services;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Diretor;
import br.com.escola.admin.repositories.DiretorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiretorService {
    private final DiretorRepository repository;

    public DiretorService(DiretorRepository repository) {
        this.repository = repository;
    }


    public List<Diretor> obterDiretores() {
        return repository.findAll();
    }

    ;

    public Diretor obterDiretorPorId(Long id) {
        Optional<Diretor> diretorOptional = repository.findById(id);
        if (diretorOptional.isEmpty()) {
            throw new ResourceNotFoundException("Diretor não encontrado");
        }
        return diretorOptional.get();
    }

    public Diretor criarDiretor(Diretor diretor) {
        Optional<Diretor> existeDiretorComEsseCpf = repository.findByCpf(diretor.getCpf());
        if (existeDiretorComEsseCpf.isPresent()) {
            throw new BusinessRuleException("Já existe um diretor com esse cpf");
        }
        repository.save(diretor);
        return diretor;
    }

    public Diretor atulizarDiretorPorId(Long id, Diretor diretor) {
        var diretorSalvo = obterDiretorPorId(id);
        Optional<Diretor> existeDiretorComEsseCpf = repository.findByCpf(diretor.getCpf());
        if (existeDiretorComEsseCpf.isPresent()) {
            throw new BusinessRuleException("Já existe um diretor com esse cpf");
        }
        diretorSalvo.setNome(diretor.getNome());
        diretorSalvo.setCpf(diretor.getCpf());
        repository.save(diretorSalvo);
        return diretorSalvo;
    }

    public void removerDiretorPorId(Long id) {
        Diretor diretor = obterDiretorPorId(id);
        repository.delete(diretor);
    }
}
