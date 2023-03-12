package br.com.escola.admin.repositories;

import java.util.List;
import java.util.Optional;

import br.com.escola.admin.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

	boolean existsByCpf(String cpf);

}
