package br.com.escola.admin.repositories;

import java.util.List;
import java.util.Optional;

import br.com.escola.admin.models.Diretor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiretorRepository extends JpaRepository<Diretor, Long> {

	boolean existsByCpf(String cpf);

}
