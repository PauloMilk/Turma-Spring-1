package br.com.escola.admin.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class CursoAlunoNota {

    @EmbeddedId
    private CursoAlunoNotaId cursoAlunoNotaId;
    private Double nota;

    public CursoAlunoNota() {
    }

    public CursoAlunoNota(CursoAlunoNotaId cursoAlunoNotaId, Double nota) {
        this.cursoAlunoNotaId = cursoAlunoNotaId;
        this.nota = nota;
    }

    public CursoAlunoNotaId getCursoAlunoNotaId() {
        return cursoAlunoNotaId;
    }

    public void setCursoAlunoNotaId(CursoAlunoNotaId cursoAlunoNotaId) {
        this.cursoAlunoNotaId = cursoAlunoNotaId;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }
}
