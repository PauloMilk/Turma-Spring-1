package br.com.escola.admin.repositories;

import br.com.escola.admin.models.Diretor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class DiretorRepositoryEmMemoria implements DiretorRepository{
    private  List<Diretor> diretores = new ArrayList<>(
            Arrays.asList(new Diretor(1L,"Diretor 1", "444.111.111-55"),
                          new Diretor(2L,"Diretor 2", "555.111.111-55"),
                          new Diretor(3L,"Diretor 3", "114.111.111-55"))
    );


    @Override
    public List<Diretor> obterDiretores() {
        return diretores;
    }

    @Override
    public Diretor salvarDiretor(Diretor diretor) {
        Long maiorId = diretores.stream()
                .mapToLong(Diretor::getId)
                                .max()
                                        .orElse(0);
        diretor.setId(maiorId+1);
        diretores.add(diretor);
        return diretor;
    }
    @Override
    public void removerDiretor(Diretor diretor) {
        diretores.remove(diretor);
    }

    @Override
    public Optional obterDiretorComCpf(String cpf) {
        return diretores.stream().filter(p -> p.getCpf().equals(cpf)).findFirst();
    }

    @Override
    public Optional<Diretor> findById(Long id) {
        return diretores.stream() .filter(p -> p.getId().equals(id)).findFirst();
    }
}
