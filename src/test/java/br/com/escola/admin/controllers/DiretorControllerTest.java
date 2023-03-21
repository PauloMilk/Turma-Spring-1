package br.com.escola.admin.controllers;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Diretor;
import br.com.escola.admin.services.DiretorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DiretorController.class)
class DiretorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiretorService diretorServiceMock;

    private static final String DIRETOR_PATH = "/diretores";

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
    }

    // ------------------------ CONSULTAR DIRETORES ------------------------ //

    // Cenário 01: não encontrar diretores e retornar 200 com lista vazia
    // Cenário 02: encontrar diretores e retornar 200 com lista de diretor

    // DADO: uma requisição
    // QUANDO: não encontrar diretores
    // ENTÃO: deve retornar 200 com uma lista vazia
    @DisplayName("Deve retornar 200 e lista vazia quando não encontrar diretores")
    @Test
    void deveRetornar200ComListaVazia() throws Exception {
        // given
        List<Diretor> listaVazia = List.of();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(DIRETOR_PATH)
                .accept(MediaType.APPLICATION_JSON);

        // when
        when(diretorServiceMock.obterTodos()).thenReturn(listaVazia);

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value("0"))
                .andDo(print());

        verify(diretorServiceMock, Mockito.times(1)).obterTodos();
    }

    // DADO: uma requisição
    // QUANDO: encontrar diretores
    // ENTÃO: deve retornar 200 com uma lista de diretor
    @DisplayName("Deve retornar 200 e lista de diretores")
    @Test
    void deveRetornar200ComListaDeDiretores() throws Exception {
        // given
        var diretor1 = new Diretor("Maria", "72967341088");
        diretor1.setId(1L);
        var diretor2 = new Diretor("Rodrigo", "60521752060");
        diretor2.setId(2L);

        List<Diretor> lista = List.of(diretor1, diretor2);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(DIRETOR_PATH)
                .accept(MediaType.APPLICATION_JSON);

        // when
        when(diretorServiceMock.obterTodos()).thenReturn(lista);

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id").value("1"))
                .andExpect(jsonPath("[0].nome").value("Maria"))
                .andExpect(jsonPath("[0].cpf").value("72967341088"))

                .andExpect(jsonPath("[1].id").value("2"))
                .andExpect(jsonPath("[1].nome").value("Rodrigo"))
                .andExpect(jsonPath("[1].cpf").value("60521752060"))
                .andDo(print());

        verify(diretorServiceMock, Mockito.times(1)).obterTodos();
    }

    // ------------------------ CONSULTAR DIRETOR ------------------------ //

    // Cenário 01: não encontrar diretor e retornar 404
    // Cenário 02: encontrar diretor e retornar 200

    // DADO: uma requisição
    // QUANDO: não encontrar diretor
    // ENTÃO: deve retornar 404 como resposta
    @DisplayName("Deve retornar 404 como resposta quando não encontrar diretor")
    @Test
    void deveRetornar404QuandoNaoEncontrar() throws Exception {
        // given
        Long id = 1L;

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(DIRETOR_PATH + "/" + id)
                .accept(MediaType.APPLICATION_JSON);

        // when
        when(diretorServiceMock.obter(anyLong())).thenThrow(new ResourceNotFoundException(id));

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("status").value("404"))
                .andExpect(jsonPath("error").value("Recurso não encontrado"))
                .andExpect(jsonPath("message").value("Recurso não encontrado. Id = " + id))
                .andExpect(jsonPath("path").value(DIRETOR_PATH + "/" + id))
                .andDo(print());

        verify(diretorServiceMock, times(1)).obter(anyLong());
    }

    // DADO: uma requisição
    // QUANDO: encontrar diretor
    // ENTÃO: deve retornar 200 e o diretor
    @DisplayName("Deve retornar 200 e o diretor com sucesso")
    @Test
    void deveRetornar200eDiretorComSucesso() throws Exception {
        // given
        var diretor = new Diretor("Maria", "72967341088");
        diretor.setId(1L);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(DIRETOR_PATH + "/" + diretor.getId())
                .accept(MediaType.APPLICATION_JSON);

        // when
        when(diretorServiceMock.obter(anyLong())).thenReturn(diretor);

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("nome").value("Maria"))
                .andExpect(jsonPath("cpf").value("72967341088"))
                .andDo(print());

        verify(diretorServiceMock, times(1)).obter(anyLong());
    }

    // ------------------------ CRIAR DIRETOR ------------------------ //

    // Cenário 01: erro ao tentar criar com dados inválidos
    // Cenário 02: erro ao tentar criar com  cpf inválido
    // Cenário 03: erro ao tentar criar para cpf existente
    // Cenário 04: sucesso ao criar para dados válidos

    // DADO: uma requisição
    // QUANDO: os dados estiverem incorretos
    // ENTÃO deve retornar 400 como resposta
    @DisplayName("Deve retornar 400 quando os dados estiverem incorretos para criar")
    @Test
    void deveRetornar400ParaDadosIncorretosAocriar() throws Exception {
        // given
        var diretorSemNome = new Diretor("", "72967341088");
        var diretorSemCpf = new Diretor("Rodrigo", "");
        var diretorSemNada = new Diretor("", "");

        String json1 = objectMapper.writeValueAsString(diretorSemNome);
        String json2 = objectMapper.writeValueAsString(diretorSemCpf);
        String json3 = objectMapper.writeValueAsString(diretorSemNada);

        // when
        MockHttpServletRequestBuilder request1 = MockMvcRequestBuilders
                .post(DIRETOR_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json1);

        MockHttpServletRequestBuilder request2 = MockMvcRequestBuilders
                .post(DIRETOR_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json2);

        MockHttpServletRequestBuilder request3 = MockMvcRequestBuilders
                .post(DIRETOR_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json3);

        // then
        // Validando diretor sem nome
        mockMvc
                .perform(request1)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("400"))
                .andExpect(jsonPath("error").value("Bad Request"))
                .andExpect(jsonPath("message").value("Verifique os campo(s) com erro"))
                .andExpect(jsonPath("path").value(DIRETOR_PATH))
                .andExpect(jsonPath("fields").value("nome"))
                .andExpect(jsonPath("fieldsMessage").value("Nome não deve ser vazio ou nulo"));

        verify(diretorServiceMock, never()).criar(any(Diretor.class));

        // Validando diretor sem cpf
        mockMvc
                .perform(request2)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("400"))
                .andExpect(jsonPath("error").value("Bad Request"))
                .andExpect(jsonPath("message").value("Verifique os campo(s) com erro"))
                .andExpect(jsonPath("path").value(DIRETOR_PATH))
                .andExpect(jsonPath("fields").value("cpf"))
                .andExpect(jsonPath("fieldsMessage").value("Cpf inválido"));

        verify(diretorServiceMock, never()).criar(any(Diretor.class));

        // Validando diretor sem nada
        mockMvc
                .perform(request3)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("400"))
                .andExpect(jsonPath("error").value("Bad Request"))
                .andExpect(jsonPath("message").value("Verifique os campo(s) com erro"))
                .andExpect(jsonPath("path").value(DIRETOR_PATH))
                .andExpect(jsonPath("fields").value("cpf, nome"))
                .andExpect(jsonPath("fieldsMessage").value("Cpf inválido, " +
                        "Nome não deve ser vazio ou nulo"))
                .andDo(print());

        verify(diretorServiceMock, never()).criar(any(Diretor.class));
    }

    // DADO: uma requisição
    // QUANDO: o cpf for inválido
    // ENTÃO deve retornar 400 como resposta
    @DisplayName("Deve retornar 400 quando o cpf for incorreto para criar")
    @Test
    void deveRetornar400ParaCpfInvalido() throws Exception {
        // given
        String cpfComDigitosiguais = "99999999999";
        String cpfFalso = "49365022868";
        String cpfSem11Digitos = "2130009484";

        var diretor1 = new Diretor("Maria", cpfComDigitosiguais);
        var diretor2 = new Diretor("Rodrigo", cpfFalso);
        var diretor3 = new Diretor("joão", cpfSem11Digitos);

        String json1 = objectMapper.writeValueAsString(diretor1);
        String json2 = objectMapper.writeValueAsString(diretor2);
        String json3 = objectMapper.writeValueAsString(diretor3);

        // when
        MockHttpServletRequestBuilder request1 = MockMvcRequestBuilders
                .post(DIRETOR_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json1);

        MockHttpServletRequestBuilder request2 = MockMvcRequestBuilders
                .post(DIRETOR_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json2);

        MockHttpServletRequestBuilder request3 = MockMvcRequestBuilders
                .post(DIRETOR_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json3);

        // then
        // Validando cpf com digitos iguais
        mockMvc
                .perform(request1)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("400"))
                .andExpect(jsonPath("error").value("Bad Request"))
                .andExpect(jsonPath("message").value("Verifique os campo(s) com erro"))
                .andExpect(jsonPath("path").value(DIRETOR_PATH))
                .andExpect(jsonPath("fields").value("cpf"))
                .andExpect(jsonPath("fieldsMessage").value("Cpf inválido"));

        verify(diretorServiceMock, never()).criar(any(Diretor.class));

        // Validando cpf falso segundo cálculo para digitos verificadores
        mockMvc
                .perform(request2)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("400"))
                .andExpect(jsonPath("error").value("Bad Request"))
                .andExpect(jsonPath("message").value("Verifique os campo(s) com erro"))
                .andExpect(jsonPath("path").value(DIRETOR_PATH))
                .andExpect(jsonPath("fields").value("cpf"))
                .andExpect(jsonPath("fieldsMessage").value("Cpf inválido"));

        verify(diretorServiceMock, never()).criar(any(Diretor.class));

        // Validando cpf que não contém 11 digitos
        mockMvc
                .perform(request3)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("400"))
                .andExpect(jsonPath("error").value("Bad Request"))
                .andExpect(jsonPath("message").value("Verifique os campo(s) com erro"))
                .andExpect(jsonPath("path").value(DIRETOR_PATH))
                .andExpect(jsonPath("fields").value("cpf"))
                .andExpect(jsonPath("fieldsMessage").value("Cpf inválido"));

        verify(diretorServiceMock, never()).criar(any(Diretor.class));
    }

    // DADO: uma requisição
    // QUANDO: o cpf for válido mas já existe
    // ENTÃO deve retornar 409 como resposta
    @DisplayName("Deve retornar 409 quando o cpf já existir para criar")
    @Test
    void deveRetornar409ParaCpfExistente() throws Exception {
        // given
        var diretor = new Diretor("Maria", "21300094842");

        String json = objectMapper.writeValueAsString(diretor);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(DIRETOR_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // when
        when(diretorServiceMock.criar(any(Diretor.class)))
                .thenThrow(new BusinessRuleException("Já existe um diretor para esse cpf"));

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("status").value("409"))
                .andExpect(jsonPath("error").value("Conflito"))
                .andExpect(jsonPath("message").value("Já existe um diretor para esse cpf"))
                .andExpect(jsonPath("path").value(DIRETOR_PATH))
                .andDo(print());

        verify(diretorServiceMock, times(1)).criar(any(Diretor.class));
    }

    // DADO: uma requisição
    // QUANDO: os dados estiverem válidos
    // ENTÃO deve retornar 201 e diretor criado
    @DisplayName("Deve retornar 201 e diretor criado com sucesso")
    @Test
    void deveRetornar201eDiretorCriadoComSucesso() throws Exception {
        // given
        var diretor = new Diretor("Maria", "21300094842");
        String json = objectMapper.writeValueAsString(diretor);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(DIRETOR_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // when
        when(diretorServiceMock.criar(any(Diretor.class))).thenReturn(diretor);

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("nome").value("Maria"))
                .andExpect(jsonPath("cpf").value("21300094842"));

        verify(diretorServiceMock, times(1)).criar(any(Diretor.class));
    }

    // ------------------------ ATUALIZAR DIRETOR ------------------------ //

    // Cenário 01: erro ao tentar atualizar com dados inválidos
    // Cenário 02: erro ao tentar atualizar para id inexistente
    // Cenário 03: sucesso ao atualizar para dados válidos

    // DAOD: uma requisição
    // QUANDO: os dados estiverem incorretos
    // ENTÃO: deve retornar 400 como resposta
    @DisplayName("Deve retornar 400 quando os dados estiverem incorretos para atualizar")
    @Test
    void deveRetornar400ParaDadosIncorretosParaAtualizar() throws Exception {
        // given
        var diretorSemNome = new Diretor("", "72967341088");
        var diretorSemCpf = new Diretor("Rodrigo", "");
        var diretorSemNada = new Diretor("", "");

        String json1 = objectMapper.writeValueAsString(diretorSemNome);
        String json2 = objectMapper.writeValueAsString(diretorSemCpf);
        String json3 = objectMapper.writeValueAsString(diretorSemNada);

        // when
        MockHttpServletRequestBuilder request1 = MockMvcRequestBuilders
                .put(DIRETOR_PATH + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json1);

        MockHttpServletRequestBuilder request2 = MockMvcRequestBuilders
                .put(DIRETOR_PATH + "/2")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json2);

        MockHttpServletRequestBuilder request3 = MockMvcRequestBuilders
                .put(DIRETOR_PATH + "/3")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json3);

        // then
        // Validando diretor sem nome
        mockMvc
                .perform(request1)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("400"))
                .andExpect(jsonPath("error").value("Bad Request"))
                .andExpect(jsonPath("message").value("Verifique os campo(s) com erro"))
                .andExpect(jsonPath("path").value(DIRETOR_PATH + "/1"))
                .andExpect(jsonPath("fields").value("nome"))
                .andExpect(jsonPath("fieldsMessage").value("Nome não deve ser vazio ou nulo"));

        verify(diretorServiceMock, never()).atualizar(anyLong(), any(Diretor.class));

        // Validando diretor sem cpf
        mockMvc
                .perform(request2)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("400"))
                .andExpect(jsonPath("error").value("Bad Request"))
                .andExpect(jsonPath("message").value("Verifique os campo(s) com erro"))
                .andExpect(jsonPath("path").value(DIRETOR_PATH + "/2"))
                .andExpect(jsonPath("fields").value("cpf"))
                .andExpect(jsonPath("fieldsMessage").value("Cpf inválido"));

        verify(diretorServiceMock, never()).atualizar(anyLong(), any(Diretor.class));

        // Validando diretor sem nada
        mockMvc
                .perform(request3)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("400"))
                .andExpect(jsonPath("error").value("Bad Request"))
                .andExpect(jsonPath("message").value("Verifique os campo(s) com erro"))
                .andExpect(jsonPath("path").value(DIRETOR_PATH + "/3"))
                .andExpect(jsonPath("fields").value("cpf, nome"))
                .andExpect(jsonPath("fieldsMessage").value("Cpf inválido, " +
                        "Nome não deve ser vazio ou nulo"))
                .andDo(print());

        verify(diretorServiceMock, never()).atualizar(anyLong(), any(Diretor.class));
    }

    // DAOD: uma requisição
    // QUANDO: não existir id
    // ENTÃO: deve retornar 404 como resposta
    @DisplayName("Deve retornar 404 quando id não existir para atualizar")
    @Test
    void deveRetornar404QuandoIdNaoExistirParaAtualizar() throws Exception {
        // given
        Long idInexistente = 1L;
        var diretor = new Diretor("Maria", "21300094842");
        String json = objectMapper.writeValueAsString(diretor);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(DIRETOR_PATH + "/" + idInexistente)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // when
        when(diretorServiceMock.atualizar(anyLong(), any(Diretor.class)))
                .thenThrow(new ResourceNotFoundException(idInexistente));

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("status").value("404"))
                .andExpect(jsonPath("error").value("Recurso não encontrado"))
                .andExpect(jsonPath("message").value("Recurso não encontrado. Id = " + idInexistente))
                .andExpect(jsonPath("path").value(DIRETOR_PATH + "/" + idInexistente))
                .andDo(print());

        verify(diretorServiceMock, times(1)).atualizar(anyLong(), any(Diretor.class));
    }

    // DAOD: uma requisição
    // QUANDO: existir id e dados estiverem corretos
    // ENTÃO: deve retornar 200 e diretor atualizado
    @DisplayName("Deve retornar 200 e diretor atualizado com sucesso")
    @Test
    void deveRetornar200eDiretorAtualizadoComSucesso() throws Exception {
        // given
        var diretor = new Diretor("Maria", "21300094842");
        diretor.setId(1L);

        String json = objectMapper.writeValueAsString(diretor);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(DIRETOR_PATH + "/" + diretor.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // when
        when(diretorServiceMock.atualizar(anyLong(), any(Diretor.class))).thenReturn(diretor);

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("nome").value("Maria"))
                .andExpect(jsonPath("cpf").value("21300094842"))
                .andDo(print());

        verify(diretorServiceMock, times(1)).atualizar(anyLong(), any(Diretor.class));
    }

    // ------------------------ DELETAR DIRETOR ------------------------ //

    // Cenário 01: erro ao tentar deletar para id inexistente
    // Cenário 02: sucesso deletar

    // DAOD: uma requisição
    // QUANDO: não existir id
    // ENTÃO: deve retornar 404 como resposta
    @DisplayName("Deve retornar 404 quando id não existir para deletar")
    @Test
    void deveRetornar404QuandoIdNaoExistirParaDeletar() throws Exception {
        // given
        Long idInexistente = 1L;

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(DIRETOR_PATH + "/" + idInexistente)
                .accept(MediaType.APPLICATION_JSON);

        // when
        when(diretorServiceMock.deletar(anyLong())).thenThrow(new ResourceNotFoundException(idInexistente));

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("status").value("404"))
                .andExpect(jsonPath("error").value("Recurso não encontrado"))
                .andExpect(jsonPath("message").value("Recurso não encontrado. Id = " + idInexistente))
                .andExpect(jsonPath("path").value(DIRETOR_PATH + "/" + idInexistente))
                .andDo(print());

        verify(diretorServiceMock, times(1)).deletar(anyLong());
    }

    // DAOD: uma requisição
    // QUANDO: existir id
    // ENTÃO: deve retornar 204 como resposta para sucesso
    @DisplayName("Deve retornar 204 quando deletar com sucesso")
    @Test
    void deveRetornar204QuandoDeletarComSucesso() throws Exception {
        // given
        Long idExistente = 1L;

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(DIRETOR_PATH + "/" + idExistente)
                .accept(MediaType.APPLICATION_JSON);

        // when
        BDDMockito.doNothing().when(diretorServiceMock).deletar(idExistente);

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(diretorServiceMock, Mockito.times(1)).deletar(idExistente);
    }

}