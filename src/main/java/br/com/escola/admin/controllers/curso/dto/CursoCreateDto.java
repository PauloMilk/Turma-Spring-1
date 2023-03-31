package br.com.escola.admin.controllers.curso.dto;

import br.com.escola.admin.models.Curso;
import jakarta.validation.constraints.NotBlank;

public record CursoCreateDto(
        @NotBlank
        String nome,

        @NotBlank
        String descricao,

        @NotBlank
        String urlImagem
) {

    public static Curso toEntity(CursoCreateDto dto) {
        return new Curso(dto.nome(), dto.descricao(), dto.urlImagem());
    }

}
