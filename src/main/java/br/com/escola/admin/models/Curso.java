package br.com.escola.admin.models;

import jakarta.persistence.*;

import java.util.UUID;


@Entity
@Table(name = "TB_CURSO")
public class Curso {

    @Id
    @Column(name = "cd_curso")
    private UUID id;


    @Column(name = "nm_curso")
    private String nome;

    @Column(name = "ds_curso")
    private String descricao;

    @Column(name = "url_imagem")
    private String imageUrl;


    @ManyToOne
    @JoinColumn(name = "cd_professor")
    private Professor professor;

    public Curso(UUID id, String nome, String descricao, String imageUrl, Professor professor) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.imageUrl = imageUrl;
        this.professor = professor;
    }

    public Curso() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Professor getProfessor() {
        return professor;
    }
}
