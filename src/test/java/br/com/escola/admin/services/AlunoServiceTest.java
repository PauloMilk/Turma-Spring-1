package br.com.escola.admin.services;

import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Aluno;
import br.com.escola.admin.repositories.AlunoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
class AlunoServiceTest {


    @Mock
    AlunoRepository mockRepository;

    @InjectMocks
    AlunoService service;


    @BeforeEach
    public void setUp() {
    }


    //Consultar Aluno por CPF

    //Dado um cpf válido
    //Quando consultar aluno por cpf
    //Então deve retornar um erro de aluno não encontrado

    @Test
    @DisplayName("Deve retornar erro quando consultar aluno por cpf inexistente")
    public void deveRetornarErroQuandoConsultarAlunoPorCpfInexistente() {
        //Given
        String cpf = "12345678900";

        //When
        Mockito.when(mockRepository.obterAlunoPorCpf(any())).thenReturn(Optional.empty());
        Throwable exception = Assertions.catchThrowable(() -> service.consultarAlunoPorCpf(cpf));

        //Then

        Assertions.assertThat(exception)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Aluno não encontrado para o CPF: " + cpf);
    }


    //Dado um cpf válido
    //Quando consultar aluno por cpf
    //Então deve retornar um aluno
    @Test
    @DisplayName("Deve retornar aluno quando consultar aluno por cpf existente")
    public void deveRetornarAlunoQuandoConsultarAlunoPorCpfExistente() {
        //Given
        String cpf = "12345678900";
        Aluno alunoMock = new Aluno("Paulo", cpf);

        //When

        Mockito.when(mockRepository.obterAlunoPorCpf("12345678900")).thenReturn(Optional.of(alunoMock));

        Aluno aluno = service.consultarAlunoPorCpf(cpf);

        //Then

        assertNotNull(aluno);
        assertEquals(cpf, aluno.getCpf());
        assertEquals("Paulo", aluno.getNome());
    }
}


class MockAlunoRepository implements AlunoRepository {

    @Override
    public Optional<Aluno> obterAlunoPorCpf(String cpf) {
        if (cpf == "12345678900") {
            return Optional.of(new Aluno("Paulo", cpf));
        }
        return Optional.empty();
    }

    @Override
    public List<Aluno> obterAlunos() {
        return null;
    }

    @Override
    public void salvarAluno(Aluno aluno) {

    }

    @Override
    public boolean existeAlunoComCpf(String cpf) {
        return false;
    }

    @Override
    public void removerAluno(Aluno aluno) {

    }

    @Override
    public Optional<Aluno> obterAlunoPorNome(String nome) {
        return Optional.empty();
    }
}