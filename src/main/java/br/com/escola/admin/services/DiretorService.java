package br.com.escola.admin.services;


import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Diretor;
import br.com.escola.admin.repositories.DiretorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DiretorService {

    private final DiretorRepository repository;

    public DiretorService(DiretorRepository repository) {
        this.repository = repository;

    }

    public List<Diretor> findAll() {
        return repository.findAll();

    }

    public Diretor findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id não encontrado"));
    }

    public boolean cpfExists(String cpf) {
        Optional<Diretor> DiretorOptional = repository.findByCpf(cpf);
        return DiretorOptional.isPresent();
    }

    public Diretor create(Diretor obj) throws BusinessRuleException {
        if (cpfExists(obj.getCpf())) {
            throw new BusinessRuleException("This CPF already exist");
        }
        return repository.save(obj);

    }

    public void delete(UUID id) {
        repository.delete(findById(id));
    }


    public Diretor update(Diretor diretor) {
        var diretorSalvo = findById(diretor.getId());

        diretorSalvo.setNome(diretor.getNome());

        return repository.save(diretorSalvo);
    }
}