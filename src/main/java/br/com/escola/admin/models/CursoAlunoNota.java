package br.com.escola.admin.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_curso_aluno_nota")
public class CursoAlunoNota implements Serializable {

    @EmbeddedId
    private CursoAlunoNotaID id;

    @Column(name = "vl_nota")
    private Double nota;

    public CursoAlunoNota() {

    }

    public CursoAlunoNota(CursoAlunoNotaID id, Double nota) {
        this.id = id;
        this.setNota(nota);
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CursoAlunoNota that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
