package br.com.escola.admin.repositories;

import br.com.escola.admin.controllers.DTO.RelatorioNotas;
import br.com.escola.admin.models.CursoAlunoNota;
import br.com.escola.admin.models.CursoAlunoNotaId;
import org.hibernate.query.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CursoAlunoNotaRepository extends JpaRepository <CursoAlunoNota, CursoAlunoNotaId> {

    @Query(nativeQuery = true,
            value = " SELECT a.nome as aluno, c.nome as curso, ca.nota FROM ALUNO a " +
            "join CURSO_ALUNO_NOTA ca on a.id = ca.aluno_id " +
            "join CURSO c on c.id = ca.curso_id")
    List<RelatorioNotas> gerarRelatorio();
}
