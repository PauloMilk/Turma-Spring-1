package br.com.escola.admin.controllers.diretor.dtos;

import br.com.escola.admin.models.Diretor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record DiretorUpdateDto(
        @NotBlank(message = "Nome não deve ser vazio ou nulo")
        @Size(max = 200)
        String nome,

        @CPF(message = "Cpf inválido")
        String cpf
) {

    public static Diretor toEntity(DiretorUpdateDto dto) {
        return new Diretor(dto.nome(), dto.cpf());
    }

}
