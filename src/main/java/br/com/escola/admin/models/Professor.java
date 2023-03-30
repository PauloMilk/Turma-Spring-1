package br.com.escola.admin.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;


@Entity
@Table(name = "tb_professor")
public class Professor {

    @Id
    @Column(name = "cd_professor")
    private UUID id;

    @Column(name = "nm_professor")
    private String nome;

    @Column(name = "cd_cpf")
    private String cpf;

    @Column(name = "ds_especialidade")
    private String especialidade;

    public Professor(UUID id, String nome, String especialidade) {
        this.id = id;
        this.nome = nome;
        this.especialidade = especialidade;
    }

    public Professor() {
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

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
