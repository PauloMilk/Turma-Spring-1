package br.com.escola.admin.repositories;

import br.com.escola.admin.models.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

//ABSTRAÇÃO
public interface AlunoRepository extends JpaRepository <Aluno,Long> {
    Aluno findByCpf(String cpf);

}
