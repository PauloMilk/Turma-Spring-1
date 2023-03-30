package br.com.escola.admin.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class CursoAlunoNotaID implements java.io.Serializable {

    @ManyToOne
    @JoinColumn(name = "cd_aluno")
    private Aluno aluno;


    @ManyToOne
    @JoinColumn(name = "cd_curso")
    private Curso curso;

    public CursoAlunoNotaID(Aluno aluno, Curso curso) {
        this.aluno = aluno;
        this.curso = curso;
    }

    public CursoAlunoNotaID() {

    }
}
