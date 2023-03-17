package br.com.escola.admin.services;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Aluno;
import br.com.escola.admin.repositories.AlunoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AlunoService {

    private final Logger logger = LoggerFactory.getLogger(AlunoService.class);

    private final AlunoRepository repository;

    public AlunoService(AlunoRepository repository) {
        this.repository = repository;
    }

    public List<Aluno> obterTodos() {
        return repository.findAll();
    }

    public Aluno obterPorId(Long id) {
        Optional<Aluno> optionalAluno = repository.findById(id);
        return optionalAluno.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Aluno salvar(Aluno aluno) {
        boolean existeAlunoComCpf = repository.existsByCpf(aluno.getCpf());

        if (existeAlunoComCpf) {
            throw new BusinessRuleException("Já existe um aluno com esse cpf");
        }

        return repository.save(aluno);
    }

    public Aluno atualizar(Long id, Aluno aluno) {
        //TODO: Arrumar bug com teste unitario
        var alunoSalvo = obterPorId(id);

        alunoSalvo.setNome(aluno.getNome());
        alunoSalvo.setCpf(aluno.getCpf());

        return repository.save(alunoSalvo);
    }

    public void deletar(Long id) {
        Aluno alunoSalvo = obterPorId(id);
        repository.delete(alunoSalvo);
    }

}
