package br.com.escola.admin.repositories;

import br.com.escola.admin.models.CursoAlunoNota;
import br.com.escola.admin.models.CursoAlunoNotaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoAlunoNotaRepository extends JpaRepository <CursoAlunoNota, CursoAlunoNotaId> {
}
