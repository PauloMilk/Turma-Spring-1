package br.com.escola.admin.services;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Aluno;
import br.com.escola.admin.repositories.AlunoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AlunoService {

    private final Logger logger = LoggerFactory.getLogger(AlunoService.class);
    //Implementação
    //Abstração
    private final AlunoRepository repository;

    public AlunoService(
            AlunoRepository repository) {
        this.repository = repository;
    }

    public Aluno consultarAlunoPorCpf(String cpf) {
        var aluno = repository.obterAlunoPorCpf(cpf);
        return aluno.orElseThrow(
                () -> new ResourceNotFoundException("Aluno não encontrado para o CPF: " + cpf)
        );
    }

    public List<Aluno> consultarAlunos() {
        return repository.obterAlunos();
    }

    public Aluno criarAluno(Aluno aluno) {
        boolean existeAlunoComCpf = repository.existeAlunoComCpf(aluno.getCpf());
        if (existeAlunoComCpf) {
            var exception = new BusinessRuleException("Já existe um aluno com esse cpf");
            logger.error(exception.getMessage());
            throw exception;
        }

        repository.salvarAluno(aluno);
        return aluno;
    }

    public Aluno atualizarAluno(String cpf, Aluno aluno) {

        //TODO: Arrumar bug com teste unitario
        var alunoSalvo = consultarAlunoPorCpf(cpf);

        alunoSalvo.setNome(aluno.getNome());
        alunoSalvo.setCpf(aluno.getCpf());


        repository.salvarAluno(alunoSalvo);

        return alunoSalvo;
    }

    public void removerAlunoPorCpf(String cpf) {

        // Agt já validou se o aluno existe na base.
        Aluno aluno = consultarAlunoPorCpf(cpf);

        repository.removerAluno(aluno);

    }

    public Aluno obterAlunoPorNome(String nome) {
        return repository.obterAlunoPorNome(nome).orElseThrow(
                () -> new ResourceNotFoundException("Aluno não encontrado")
        );
    }
}
