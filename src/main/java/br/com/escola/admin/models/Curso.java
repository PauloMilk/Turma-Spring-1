package br.com.escola.admin.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.Objects;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Entity
@Table(name = "tb_curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_curso")
    private Long id;

    @Column(name = "nm_curso", nullable = false)
    private String nome;

    @Column(name = "ds_curso", nullable = false)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "cd_professor", nullable = true)
    private Professor professor;

    @Column(name = "url_imagem", nullable = true)
    private String urlImagem;

//    @JsonIgnore
//    @ManyToMany(mappedBy = "cursos")
//    private Set<Aluno> alunos = new HashSet<>();

    public Curso() {

    }

    public Curso(String nome, String descricao, String urlImagem) {
        this.nome = nome;
        this.descricao = descricao;
        this.urlImagem = urlImagem;
    }

    public Curso(String nome, String descricao, String urlImagem, Professor professor) {
        this.nome = nome;
        this.descricao = descricao;
        this.urlImagem = urlImagem;
        this.professor = professor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

//    public Set<Aluno> getAlunos() {
//        return alunos;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Curso curso)) return false;
        return Objects.equals(getId(), curso.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
