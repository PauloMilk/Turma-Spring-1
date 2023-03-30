package br.com.escola.admin.repositories;

import br.com.escola.admin.models.Diretor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface DiretorRepository extends JpaRepository<Diretor, UUID> {

    Optional<Diretor> findByCpf(String cpf);

}