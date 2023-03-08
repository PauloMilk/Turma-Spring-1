package br.com.escola.admin.models;

public class Professor {

	private Long id;
	private String nome;
	private String cpf;
	private String especialidade;

	public Professor() {

	}
	
	public Professor(Long id, String nome, String cpf, String especialidade) {
		this.id = id;
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
