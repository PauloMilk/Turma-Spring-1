package br.com.escola.admin.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_professor")
public class Professor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome", nullable = false, length = 250)
	private String nome;

	@Column(name = "cpf", nullable = false, length = 11)
	private String cpf;

	@Column(name = "especialidade", nullable = false, length = 150)
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
	
}
