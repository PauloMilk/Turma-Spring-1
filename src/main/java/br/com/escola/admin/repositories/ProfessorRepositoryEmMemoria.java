package br.com.escola.admin.repositories;

import br.com.escola.admin.models.Professor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class ProfessorRepositoryEmMemoria implements ProfessorRepository{
    private List<Professor> professores = new ArrayList<>(
            Arrays.asList(new Professor(1L,"João", "444.111.111-55", "C#"),
                          new Professor(2L,"Paulo", "555.111.111-55", "Java"),
                          new Professor(3L,"Maria", "114.111.111-55", "JavaScript"))
    );
    @Override
    public Optional<Professor> obterProfessorPorCpf(String cpf) {
        return Optional.empty();
    }

    @Override
    public List<Professor> obterProfessores() {
        return professores;
    }

    @Override
    public Professor salvarProfessor(Professor professor) {
        Long maiorId = professores.stream()
                .mapToLong(Professor::getId)
                                .max()
                                        .orElse(0);
        professor.setId(maiorId+1);
        professores.add(professor);
        return professor;
    }

    @Override
    public boolean existeProfessorComCpf(String cpf) {
        return professores.stream().anyMatch(p -> p.getCpf().equals(cpf));
    }

    @Override
    public void removerProfessor(Professor professor) {
        professores.remove(professor);

    }

    public Optional<Professor> findById(Long id) {
        return professores.stream().filter(p -> p.getId().equals(id)).findFirst();
    }
}
