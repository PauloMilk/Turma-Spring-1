package br.com.escola.admin.repositories;

import br.com.escola.admin.models.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//ABSTRAÇÃO
public interface AlunoRepository extends JpaRepository<Aluno,Long> {
    Optional<Aluno> findByCpf(String cpf);

}
