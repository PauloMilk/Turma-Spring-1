package br.com.escola.admin.controllers.aluno.dto;

import br.com.escola.admin.models.Aluno;

public record AlunoDTO(
        String nome,
        String cpf
) {

    public static AlunoDTO from(Aluno aluno) {
        return new AlunoDTO(aluno.getNome(), aluno.getCpf());
    }

    public static Aluno to(AlunoDTO alunoDTO) {
        return new Aluno(alunoDTO.nome(), alunoDTO.cpf());
    }
}
