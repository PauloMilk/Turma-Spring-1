package br.com.escola.admin.repositories;

import br.com.escola.admin.models.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaAlunoRepository extends JpaRepository<Aluno, String> {

    @Query(nativeQuery = true, value = "SELECT * FROM aluno WHERE nome = ?1")
    Optional<Aluno> obterAlunoPorNome(String nome);
}
