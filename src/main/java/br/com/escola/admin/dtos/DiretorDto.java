package br.com.escola.admin.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class DiretorDto implements Serializable {

    @NotBlank
    @Size(max = 250)
    private String nome;

    @NotBlank
    @Size(min = 11, max = 11)
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
