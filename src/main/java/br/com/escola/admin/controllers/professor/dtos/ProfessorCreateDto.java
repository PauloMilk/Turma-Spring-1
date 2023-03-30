package br.com.escola.admin.controllers.professor.dtos;

import br.com.escola.admin.models.Aluno;
import br.com.escola.admin.models.Professor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record ProfessorCreateDto(
        @NotBlank(message = "Nome não deve ser vazio ou nulo")
        @Size(max = 200)
        String nome,

        @CPF(message = "Cpf inválido")
        String cpf,

        @NotBlank(message = "Especialidade não deve ser vazio ou nulo")
        String especialidade
) {

    public static Professor toEntity(ProfessorCreateDto dto) {
        return new Professor(dto.nome(), dto.cpf(), dto.especialidade());
    }

}
