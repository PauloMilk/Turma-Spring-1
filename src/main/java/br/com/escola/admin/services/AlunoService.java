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

    public Aluno consultarAlunoPorId(Long id) {

        Optional<Aluno> alunoSelecionado = repository.findById(id);

        if (alunoSelecionado.isEmpty()) {
            var exception = new ResourceNotFoundException("Aluno não existe");
            logger.debug(exception.getMessage());
            throw exception;
        }

        return alunoSelecionado.get();
    }

    public List<Aluno> consultarAlunos() {
        return repository.findAll();
    }

    public Aluno criarAluno(Aluno aluno) {
        // nao pode add um aluno com mesmo cpf
        //saber se existe um aluno com o cpf informado
        Optional<Aluno> existeAlunoComCpf = repository.findByCpf(aluno.getCpf());
        if (existeAlunoComCpf.isPresent()) {
            var exception = new BusinessRuleException("Já existe um aluno com esse cpf");
            logger.error(exception.getMessage());
            throw exception;
        }

        repository.save(aluno);
        return aluno;
    }

    public Aluno atualizarAluno(Long id, Aluno aluno) {
        //TODO: Arrumar bug com teste unitario
        var alunoSalvo = consultarAlunoPorId(id);
        Optional<Aluno> existeAlunoComEsseCpf = repository.findByCpf(aluno.getCpf());
        if (existeAlunoComEsseCpf.isPresent()) {
            throw new BusinessRuleException("Já existe um diretor com esse cpf");
        }

        alunoSalvo.setNome(aluno.getNome());
        alunoSalvo.setCpf(aluno.getCpf());

        repository.save(alunoSalvo);

        return alunoSalvo;
    }

    public void removerAlunoPorId(Long id) {

        // Agt já validou se o aluno existe na base.
        Aluno aluno = consultarAlunoPorId(id);

        repository.delete(aluno);

    }
}
