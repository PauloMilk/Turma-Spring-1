package br.com.escola.admin.controllers.aluno.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public class AlunoPostRequest {

    @NotBlank
    private String nome;

    @NotBlank @CPF
    private String cpf;

    public AlunoPostRequest() {
    }

    public AlunoPostRequest(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
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
