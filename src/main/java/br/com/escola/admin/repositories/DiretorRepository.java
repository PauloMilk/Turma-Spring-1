package br.com.escola.admin.repositories;

import br.com.escola.admin.models.Diretor;

import java.util.List;
import java.util.Optional;

public interface DiretorRepository {

    Optional obterDiretorComCpf(String cpf);
    Optional<Diretor> findById(Long id);

    List<Diretor> obterDiretores();

    Diretor salvarDiretor(Diretor diretor);


    void removerDiretor(Diretor diretor);
}
