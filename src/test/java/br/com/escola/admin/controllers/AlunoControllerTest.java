package br.com.escola.admin.controllers;

import br.com.escola.admin.controllers.aluno.AlunoController;
import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Aluno;
import br.com.escola.admin.services.AlunoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlunoController.class)
class AlunoControllerTest {


    private static final String ALUNO_PATH = "/alunos";
    @Autowired
    MockMvc mvc;
    @MockBean
    AlunoService mockService;


    // ---------- CONSULTA DE LISTA DE ALUNOS ---------- //

    // DADO: uma request
    // QUANDO: não houver alunos
    // ENTÃO: deve retornar 200 com uma lista vazia
    @Test
    @DisplayName("Deve retornar 200 quando não houver alunos")
    void deveRetornar200QuandoNaoHouverAlunos() throws Exception {
        // Given
        List<Aluno> listaEsperada = List.of();
        given(mockService.consultarAlunos()).willReturn(listaEsperada);
        // When

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(ALUNO_PATH)
                .accept(MediaType.APPLICATION_JSON);

        // Then

        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(0));
    }

    // DADO: uma request
    // QUANDO: houver alunos
    // ENTÃO: deve retornar 200 com uma lista dos alunos
    @Test
    @DisplayName("Deve retornar 200 quando houver alunos")
    void deveRetornar200QuandoHouverAlunos() throws Exception {
        // Given
        List<Aluno> listaEsperada = List.of(
                new Aluno("João da Silva", "12345678900"),
                new Aluno("Maria da Silva", "12345678901")
        );
        given(mockService.consultarAlunos()).willReturn(listaEsperada);
        // When

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(ALUNO_PATH)
                .accept(MediaType.APPLICATION_JSON);

        // Then
        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2))

                .andExpect(jsonPath("[0].nome").value("João da Silva"))
                .andExpect(jsonPath("[0].cpf").value("12345678900"))

                .andExpect(jsonPath("[1].nome").value("Maria da Silva"))
                .andExpect(jsonPath("[1].cpf").value("12345678901"));
    }


    // ---------- CONSULTA DE ALUNO ---------- //

    // DADO: uma request
    // QUANDO: informar cpf invalido
    // ENTÃO: deve retornar 400 com a mensagem de cpf invalido
    @Test
    @DisplayName("Deve retornar 400 quando informar cpf invalido")
    void deveRetornar400ParaCPFInvalido() throws Exception {
        // Given
        String cpf = "12332112312";

        // When

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(ALUNO_PATH + "/" + cpf)
                .accept(MediaType.APPLICATION_JSON);

        // Then

        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("CPF inválido"))
                .andExpect(jsonPath("status").value("400"));

        verify(mockService, never()).consultarAlunoPorCpf(cpf);

    }

    // DADO: uma request
    // QUANDO: informar cpf valido porem inexistente
    // ENTÃO: deve retornar 404
    @Test
    @DisplayName("Deve retornar 404 quando informar cpf valido porem inexistente")
    void deveRetornar404ParaCPFInexistente() throws Exception {
        // Given
        String cpf = "57788052094";

        given(mockService.consultarAlunoPorCpf(cpf)).willThrow(new ResourceNotFoundException("Aluno não encontrado"));
        // When

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(ALUNO_PATH + "/" + cpf)
                .accept(MediaType.APPLICATION_JSON);

        // Then
        mvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("Aluno não encontrado"))
                .andExpect(jsonPath("status").value("404"));

        verify(mockService, times(1)).consultarAlunoPorCpf(cpf);

    }

    // DADO: uma request
    // QUANDO: informar cpf valido
    // ENTÃO: deve retornar o usuario encontrado
    @Test
    @DisplayName("Deve retornar 200 e o usuario ")
    void deveRetornar200EUsuario() throws Exception {
        // Given
        String cpf = "57788052094";

        given(mockService.consultarAlunoPorCpf(cpf)).willReturn(
                new Aluno("João da Silva", "57788052094")
        );
        // When

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(ALUNO_PATH + "/" + cpf)
                .accept(MediaType.APPLICATION_JSON);

        // Then
        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("nome").value("João da Silva"))
                .andExpect(jsonPath("cpf").value("57788052094"));


        verify(mockService, times(1)).consultarAlunoPorCpf(cpf);

    }


    // ---------- CRIAÇÃO DE ALUNO ---------- //

    // DADO: uma request
    // QUANDO: não informar cpf ou nome ou nada
    // ENTÃO: deve retornar 400 com a mensagem do respectivo erro
    @Test
    @DisplayName("Deve retornar 400 quando não informar cpf ou nome")
    void deveRetornar400QuandoNaoInformarCpfOuNome() throws Exception {
        // Given
        Aluno alunoSemNome = new Aluno("", "81716248043");
        Aluno alunoSemCpf = new Aluno("Joao da Silva", "");
        Aluno alunoSemNada = new Aluno("", "");

        String jsonAlunoSemNome = new ObjectMapper().writeValueAsString(alunoSemNome);
        String jsonAlunoSemCpf = new ObjectMapper().writeValueAsString(alunoSemCpf);
        String jsonAlunoSemNada = new ObjectMapper().writeValueAsString(alunoSemNada);

        // When

        MockHttpServletRequestBuilder requestAlunoSemNome = MockMvcRequestBuilders
                .post(ALUNO_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonAlunoSemNome);


        MockHttpServletRequestBuilder requestAlunoSemCpf = MockMvcRequestBuilders
                .post(ALUNO_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonAlunoSemCpf);

        MockHttpServletRequestBuilder requestAlunoSemNada = MockMvcRequestBuilders
                .post(ALUNO_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonAlunoSemNada);


        // Then

        // Validacao de Nome nulo
        mvc
                .perform(requestAlunoSemNome)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Nome não pode ser nulo"))
                .andExpect(jsonPath("status").value("400"));

        verify(mockService, never()).criarAluno(any());


        // Validacao de CPF nulo
        mvc
                .perform(requestAlunoSemCpf)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("CPF não pode ser nulo"))
                .andExpect(jsonPath("status").value("400"));

        verify(mockService, never()).criarAluno(any());


        // Validacao de zero atributos (Deve validar primeiro o cpf)
        mvc
                .perform(requestAlunoSemNada)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("CPF não pode ser nulo"))
                .andExpect(jsonPath("status").value("400"));

        verify(mockService, never()).criarAluno(any());
    }

    // DADO: uma request
    // QUANDO: informar cpf invalido
    // ENTÃO: deve retornar 400 com a mensagem de cpf invalido
    @Test
    @DisplayName("Deve retornar 400 quando informar cpf invalido")
    void deveRetornar400QuandoInformarCpfInvalido() throws Exception {
        // Given
        Aluno alunoComCpfInvalido = new Aluno("Joao da Silva", "12345678901");

        String jsonAlunoComCpfInvalido = new ObjectMapper().writeValueAsString(alunoComCpfInvalido);

        // When

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(ALUNO_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonAlunoComCpfInvalido);

        // Then

        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("CPF inválido"))
                .andExpect(jsonPath("status").value("400"));

        verify(mockService, never()).criarAluno(any());

    }

    // DADO: uma request
    // QUANDO: informar cpf valido porem ja existente
    // ENTÃO: deve retornar 400 com a mensagem de cpf ja existente
    @Test
    @DisplayName("Deve retornar 400 quando informar cpf ja existente")
    void deveRetornar400QuandoInformarCpfJaExistente() throws Exception {
        // Given
        Aluno aluno = new Aluno("Joao da Silva", "57788052094");

        String json = new ObjectMapper().writeValueAsString(aluno);

        given(mockService.criarAluno(any())).willThrow(new BusinessRuleException("CPF já existente"));

        // When

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(ALUNO_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        // Then

        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("CPF já existente"))
                .andExpect(jsonPath("status").value("400"));

        verify(mockService, times(1)).criarAluno(any());

    }

    // DADO: uma request
    // QUANDO: informar cpf valido e nome valido
    // ENTÃO: deve retornar 201 com o aluno criado
    @Test
    @DisplayName("Deve retornar 201 quando informar cpf e nome validos")
    void deveCriarAlunoComSucesso() throws Exception {
        // Given
        Aluno aluno = new Aluno("João da Silva", "87802236053");

        String json = new ObjectMapper().writeValueAsString(aluno);

        given(mockService.criarAluno(any())).willReturn(aluno);

        // When

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(ALUNO_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        // Then
        mvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("nome").value("João da Silva"))
                .andExpect(jsonPath("cpf").value("87802236053"));

    }

}