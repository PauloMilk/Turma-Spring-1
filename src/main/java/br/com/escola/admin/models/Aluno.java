package br.com.escola.admin.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_aluno")
public class Aluno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_aluno")
    private Long id;

    @Column(name = "nm_aluno", nullable = false, length = 250)
    @NotBlank(message = "O nome do aluno não deve ser vazio ou nulo")
    private String nome;

    @Column(name = "cd_cpf", nullable = false, length = 11)
    @NotBlank(message = "O cpf do aluno não deve ser vazio ou nulo")
    private String cpf;

    @ManyToMany
    @JoinTable(name = "tb_aluno_curso",
            joinColumns = @JoinColumn(name = "cd_aluno"),
            inverseJoinColumns = @JoinColumn(name = "cd_curso")
    )
    private Set<Curso> cursos = new HashSet<>();

    public Aluno() {

    }

    public Aluno(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public boolean estaMatriculado(Curso curso) {
        return this.cursos.contains(curso);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Curso> getCursos() {
        return cursos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aluno aluno)) return false;
        return Objects.equals(getId(), aluno.getId()) && Objects.equals(getCpf(), aluno.getCpf());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCpf());
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}
