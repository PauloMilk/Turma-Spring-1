package br.com.escola.admin.repositories;

import br.com.escola.admin.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor,Long> {
    Optional<Professor> findByCpf(String cpf);
}
