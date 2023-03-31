package br.com.escola.admin.controllers.curso.dto;

import br.com.escola.admin.models.Curso;
import jakarta.validation.constraints.NotBlank;

public record CursoUpdateDto(
        @NotBlank
        String nome,

        @NotBlank
        String descricao,

        @NotBlank
        String urlImagem
) {

    public static Curso toEntity(CursoUpdateDto dto) {
        return new Curso(dto.nome(), dto.descricao(), dto.urlImagem());
    }

}
