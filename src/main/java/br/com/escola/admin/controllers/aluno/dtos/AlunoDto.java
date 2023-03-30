package br.com.escola.admin.controllers.aluno.dtos;

import br.com.escola.admin.models.Aluno;

public record AlunoDto(
        Long id,
        String nome,
        String cpf
) {

    public static AlunoDto from(Aluno aluno) {
        return new AlunoDto(aluno.getId(), aluno.getNome(), aluno.getCpf());
    }

    public static Aluno to(AlunoDto alunoDto) {
        return new Aluno(alunoDto.nome(), alunoDto.cpf());
    }

}
