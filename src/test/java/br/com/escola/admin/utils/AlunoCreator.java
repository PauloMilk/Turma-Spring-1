package br.com.escola.admin.utils;

import br.com.escola.admin.models.Aluno;

public class AlunoCreator {

    public static Aluno criarAlunoParaSalvar() {
        return new Aluno("Maria de Andrade", "06065002003");
    }

    public static Aluno criarAlunoValido() {
        Aluno aluno = new Aluno("Maria de Andrade", "06065002003");
        aluno.setId(1L);
        return aluno;
    }

    public static Aluno criarAlunoValidoParaAtualizar() {
        Aluno aluno = new Aluno("João Pedro Lima", "06065002003");
        aluno.setId(1L);
        return aluno;
    }

}
