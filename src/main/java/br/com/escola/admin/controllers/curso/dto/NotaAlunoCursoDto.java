package br.com.escola.admin.controllers.curso.dto;

import br.com.escola.admin.models.CursoAlunoNota;

import java.math.BigDecimal;

public record NotaAlunoCursoDto(
        BigDecimal nota
) {

        public static CursoAlunoNota to(NotaAlunoCursoDto dto) {
                CursoAlunoNota cursoAlunoNota = new CursoAlunoNota();
                cursoAlunoNota.setNota(dto.nota().intValue());
                return cursoAlunoNota;
        }

}
