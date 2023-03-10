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
    //Implementação
    //Abstração
    private final AlunoRepository repository;

    public AlunoService(
            AlunoRepository repository) {
        this.repository = repository;
    }

    public Aluno consultarAlunoPorCpf(String cpf) {

        Optional<Aluno> alunoSelecionado = repository.obterAlunoPorCpf(cpf);

        if (alunoSelecionado.isEmpty()) {
            var exception = new ResourceNotFoundException("Aluno não existe");
            logger.debug(exception.getMessage());
            throw exception;
        }

        return alunoSelecionado.get();
    }

    public List<Aluno> consultarAlunos() {
        return repository.obterAlunos();
    }

    public Aluno criarAluno(Aluno aluno) {
        // nao pode add um aluno com mesmo cpf
        //saber se existe um aluno com o cpf informado
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
}
