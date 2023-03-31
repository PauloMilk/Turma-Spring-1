package br.com.escola.admin.controllers.curso.dto;

import br.com.escola.admin.models.Curso;
import br.com.escola.admin.models.Professor;

public record CursoDto(
        Long id,
        String nome,
        String descricao,
        String urlImagem,
        Professor professor
) {

    public static CursoDto from(Curso curso) {
        return new CursoDto(
                curso.getId(),
                curso.getNome(),
                curso.getDescricao(),
                curso.getUrlImagem(),
                curso.getProfessor()
        );
    }

    public static Curso to(CursoDto dto) {
        return new Curso(dto.nome(), dto.descricao(), dto.urlImagem(), dto.professor());
    }

}
