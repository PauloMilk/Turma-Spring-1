package br.com.escola.admin.repositories;

import br.com.escola.admin.models.Diretor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DiretorRepository extends JpaRepository<Diretor,Long> {
    Optional<Diretor> findByCpf(String cpf);
}
