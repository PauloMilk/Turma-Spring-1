package br.com.escola.admin.controllers.professor.dtos;

import br.com.escola.admin.models.Professor;

public record ProfessorDto(
        Long id,
        String nome,
        String cpf,
        String especialidade
) {

    public static ProfessorDto from(Professor professor) {
        return new ProfessorDto(
            professor.getId(),
                professor.getNome(),
                professor.getCpf(),
                professor.getEspecialidade()
        );
    }

    public static Professor to(ProfessorDto dto) {
        return new Professor(dto.nome(), dto.cpf(), dto.especialidade());
    }

}
