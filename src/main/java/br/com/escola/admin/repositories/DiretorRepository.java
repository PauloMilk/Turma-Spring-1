package br.com.escola.admin.repositories;

import br.com.escola.admin.models.Diretor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiretorRepository extends JpaRepository < Diretor, Long> {

    Diretor findByCpf(String cpf);

}
