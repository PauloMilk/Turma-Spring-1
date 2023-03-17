package br.com.escola.admin.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Entity
@Table(name = "tb_professor")
public class Professor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome", nullable = false, length = 250)
	@NotBlank(message = "O nome do professor não deve ser vazio ou nulo")
	private String nome;

	@Column(name = "cpf", nullable = false, length = 11)
	@NotBlank(message = "O cpf do professor não deve ser vazio ou nulo")
	private String cpf;

	@Column(name = "especialidade", nullable = false, length = 150)
	@NotBlank(message = "A especialidade do professor não deve ser vazio ou nulo")
	private String especialidade;

	public Professor() {

	}
	
	public Professor(String nome, String cpf, String especialidade) {
		this.nome = nome;
		this.cpf = cpf;
		this.especialidade = especialidade;
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
}
