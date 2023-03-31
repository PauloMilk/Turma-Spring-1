package br.com.escola.admin.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CursoAlunoNotaID implements Serializable {

    @ManyToOne
    @JoinColumn(name = "cd_aluno")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "cd_curso")
    private Curso curso;

    public CursoAlunoNotaID() {

    }

    public CursoAlunoNotaID(Aluno aluno, Curso curso) {
        this.aluno = aluno;
        this.curso = curso;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CursoAlunoNotaID that)) return false;
        return Objects.equals(getAluno(), that.getAluno()) && Objects.equals(getCurso(), that.getCurso());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAluno(), getCurso());
    }

}
