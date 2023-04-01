package br.com.escola.admin.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CursoAlunoNotaId implements Serializable {
    private Long aluno_id;
    private Long curso_id;

    public CursoAlunoNotaId() {
    }

    public CursoAlunoNotaId(Long aluno_id, Long curso_id) {
        this.aluno_id = aluno_id;
        this.curso_id = curso_id;
    }

    public Long getAluno_id() {
        return aluno_id;
    }

    public void setAluno_id(Long aluno_id) {
        this.aluno_id = aluno_id;
    }

    public Long getCurso_id() {
        return curso_id;
    }

    public void setCurso_id(Long curso_id) {
        this.curso_id = curso_id;
    }
}
