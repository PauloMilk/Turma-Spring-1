package br.com.escola.admin.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;

@Builder
public class ProfessorDto implements Serializable {

    @NotBlank(message = "Nome não deve ser vazio ou nulo")
    @Size(max = 250)
    private String nome;

    @CPF(message = "Cpf inválido")
    private String cpf;

    @NotBlank(message = "Especialidade não deve ser vazio ou nulo")
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
