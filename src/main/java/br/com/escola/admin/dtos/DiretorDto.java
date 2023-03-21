package br.com.escola.admin.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;

public class DiretorDto implements Serializable {

    @NotBlank(message = "Nome não deve ser vazio ou nulo")
    @Size(max = 250)
    private String nome;

    @CPF(message = "Cpf inválido")
    private String cpf;

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
