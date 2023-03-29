package br.com.escola.admin.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CursoAlunoNotaId implements Serializable {
    private Long alunoId;
    private Long cursoId;

    public CursoAlunoNotaId() {
    }

    public CursoAlunoNotaId(Long alunoId, Long cursoId) {
        this.alunoId = alunoId;
        this.cursoId = cursoId;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }
}
