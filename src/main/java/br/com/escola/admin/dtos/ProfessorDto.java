package br.com.escola.admin.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class ProfessorDto implements Serializable {

    @NotBlank(message = "O nome do professor não deve ser vazio ou nulo")
    @Size(max = 250)
    private String nome;

    @NotBlank(message = "O cpf do professor não deve ser vazio ou nulo")
    @Size(min = 11, max = 11)
    private String cpf;

    @NotBlank(message = "A especialidade do professor não deve ser vazio ou nulo")
    @Size(max = 150)
    private String especialidade;

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

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

}
