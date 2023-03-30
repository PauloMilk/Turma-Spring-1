package br.com.escola.admin.controllers.aluno.dtos;

import br.com.escola.admin.models.Aluno;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record AlunoCreateDto(
        @NotBlank(message = "Nome não deve ser vazio ou nulo")
        @Size(max = 200)
        String nome,

        @CPF(message = "Cpf inválido")
        String cpf
) {
    public static Aluno toEntity(AlunoCreateDto dto) {
        return new Aluno(dto.nome(), dto.cpf());
    }

}
