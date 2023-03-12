package br.com.escola.admin.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "tb_aluno")
public class Aluno implements Serializable {

    @Id
    @Column(name = "cpf", length = 11)
    private String cpf;

    @Column(name = "nome", length = 250)
    private String nome;

    public Aluno() {

    }

    public Aluno(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }
}
