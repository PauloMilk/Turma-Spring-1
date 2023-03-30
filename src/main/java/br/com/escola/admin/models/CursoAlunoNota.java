package br.com.escola.admin.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "CURSO_ALUNO_NOTA")
public class CursoAlunoNota {

    @EmbeddedId
    private CursoAlunoNotaID id;


    @Column(name = "nr_nota")
    private Double nota;

    public CursoAlunoNota(CursoAlunoNotaID id, Double nota) {
        this.id = id;
        this.nota = nota;
    }

    public CursoAlunoNota() {
    }

    public CursoAlunoNotaID getId() {
        return id;
    }

    public void setId(CursoAlunoNotaID id) {
        this.id = id;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }
}
