package br.com.escola.admin.models;

import jakarta.persistence.*;

@Entity
@Table(name="TB_PROFESSOR")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome",nullable = false)
    private String nome;
    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;
    @Column(name = "especialidade",nullable = false)
    private String especialidade;

    public Professor(String nome, String cpf, String especialidade) {
        this.nome = nome;
        this.cpf = cpf;
        this.especialidade = especialidade;
    }

    public Professor() {
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
