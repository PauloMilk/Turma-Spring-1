package br.com.escola.admin.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "tb_diretor")
public class Diretor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_diretor")
    private Long id;
    @NotEmpty(message = "nome não pode ser vazio")
    @Column(name = "nm_diretor")
    private String nome;
    @NotEmpty(message = "cpf não pode ser vazio")
    @Column(name = "nr_cpf", length = 11)
    private String cpf;

    public Diretor(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public Diretor() {

    }

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
    }

}
