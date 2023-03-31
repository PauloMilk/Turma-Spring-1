package br.com.escola.admin.repositories;

import br.com.escola.admin.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {

}
