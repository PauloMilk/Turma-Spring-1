package br.com.escola.admin.models;

import java.util.UUID;

public class Professor {

    private final String id;
    private String nome;
    private String cpf;
    private String especialidade;

    public Professor(String nome, String cpf, String especialidade) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.cpf = cpf;
        this.especialidade = especialidade;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
