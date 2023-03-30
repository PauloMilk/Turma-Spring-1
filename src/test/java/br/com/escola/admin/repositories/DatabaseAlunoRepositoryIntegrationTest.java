package br.com.escola.admin.repositories;


import br.com.escola.admin.models.Aluno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class DatabaseAlunoRepositoryIntegrationTest {

    //Obter aluno por cpf:
    // Cenario 2: Deve retornar vazio quando o cpf não existir
    // Cenario 1: Deve retornar um aluno quando o cpf existir

    @Autowired
    JpaAlunoRepository jpa;

    DatabaseAlunoRepository repository;

    @BeforeEach
    void setUp() {
        jpa.deleteAll();
        this.repository = new DatabaseAlunoRepository(jpa);
    }

    @Test
    @DisplayName("Deve retornar um aluno quando o cpf existir")
    void deveRetornarUmAlunoQuandoOCpfExistir() {
        // GIVEN
        String cpf = "12345678900";
        var aluno = new Aluno("Fulano", cpf);
        jpa.save(aluno);

        // WHEN
        var alunoOp = repository.obterAlunoPorCpf(cpf);

        // THEN
        assertThat(alunoOp).isNotEmpty();
        assertThat(alunoOp.get().getCpf()).isEqualTo("12345678900");
        assertThat(alunoOp.get().getNome()).isEqualTo("Fulano");

    }

    @Test
    @DisplayName("Deve retornar vazio quando o cpf não existir")
    void deveRetornarVazioQuandoOCpfNaoExistir() {
        // GIVEN
        String cpf = "12345678900";

        // WHEN
        var alunoOp = repository.obterAlunoPorCpf(cpf);

        // THEN
        assertThat(alunoOp).isEmpty();
    }


    //Salvar aluno:

    // Cenario 1: Deve salvar um aluno
    @Test
    @DisplayName("Deve salvar um aluno")
    void deveSalvarUmAluno() {
        // GIVEN
        String cpf = "12345678900";
        var aluno = new Aluno("Fulano", cpf);

        // WHEN
        repository.salvarAluno(aluno);

        // THEN
        var alunoOp = jpa.findById(cpf);

        assertThat(alunoOp).isNotEmpty();
        assertThat(alunoOp.get().getCpf()).isEqualTo("12345678900");
        assertThat(alunoOp.get().getNome()).isEqualTo("Fulano");
    }


    //Atualizar
    @Test
    @DisplayName("Deve atualizar um aluno")
    void deveAtualizarUmAluno() {
        //Given
        String cpf = "12345678900";
        var aluno = new Aluno("Fulano", cpf);
        jpa.save(aluno);

        //When

        aluno.setNome("Ciclano");
        repository.salvarAluno(aluno);

        //Then
        var alunoSalvo = repository.obterAlunoPorCpf(cpf);
        assertThat(alunoSalvo.get().getNome()).isEqualTo("Ciclano");
    }

}