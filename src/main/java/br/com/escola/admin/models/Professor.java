package br.com.escola.admin.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id"
)
@Entity
@Table(name = "tb_professor")
public class Professor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cd_professor")
	private Long id;

	@Column(name = "nm_professor", nullable = false, length = 250)
	@NotBlank(message = "O nome do professor não deve ser vazio ou nulo")
	private String nome;

	@Column(name = "cd_cpf", nullable = false, length = 11)
	@NotBlank(message = "O cpf do professor não deve ser vazio ou nulo")
	private String cpf;

	@Column(name = "ds_especialidade", nullable = false, length = 150)
	@NotBlank(message = "A especialidade do professor não deve ser vazio ou nulo")
	private String especialidade;

	@OneToMany(mappedBy = "professor")
	private Set<Curso> cursos = new HashSet<>();

	public Professor() {

	}
	
	public Professor(String nome, String cpf, String especialidade) {
		this.nome = nome;
		this.cpf = cpf;
		this.especialidade = especialidade;
	}

	public void adicionarCurso(Curso curso) {
		this.cursos.add(curso);
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

	public Set<Curso> getCursos() {
		return cursos;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Professor professor)) return false;
		return Objects.equals(getId(), professor.getId()) && Objects.equals(getCpf(), professor.getCpf());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getCpf());
	}

	@Override
	public String toString() {
		return "Professor{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", cpf='" + cpf + '\'' +
				", especialidade='" + especialidade + '\'' +
				'}';
	}
}
