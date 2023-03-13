package br.com.escola.admin.repositories;

import br.com.escola.admin.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository  extends JpaRepository <Professor, Long> {

    Professor findByCpf(String cpf);
}
