package br.com.escola.admin.controllers;

import br.com.escola.admin.controllers.aluno.AlunoController;
import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Aluno;
import br.com.escola.admin.services.AlunoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AlunoController.class)
class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlunoService alunoServiceMock;

    private ObjectMapper objectMapper;

    private static final String ALUNO_PATH = "/alunos";

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
    }

    // ---------------------- CONSULTAR LISTA DE ALUNOS ---------------------- //

    // DADO: requisição
    // QUANDO: não houver alunos
    // ENTÃO: deve retornar 200 com uma lista vazia
    @DisplayName("Deve retornar 200 quando não houver alunos")
    @Test
    void deveRetornar200QuandoNaoHouverAlunos() throws Exception {
        // given
        List<Aluno> listaEsperada = List.of();
        BDDMockito.given(alunoServiceMock.obterTodos()).willReturn(listaEsperada);

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(ALUNO_PATH)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("length()").value(0));
    }

    // DADO: requisição
    // QUANDO: houver alunos
    // ENTÃO: deve retornar 200 com uma lista de aluno
    @DisplayName("Deve retornar 200 quando houver alunos")
    @Test
    void deveRetornar200QuandoHouverAlunos() throws Exception {
        // given
        var aluno1 = new Aluno("Maria de Andrade", "06065002003");
        aluno1.setId(1L);
        var aluno2 = new Aluno("João Pedro Lima", "16345356000");
        aluno2.setId(2L);

        List<Aluno> listaEsperada = List.of(aluno1, aluno2);

        BDDMockito.given(alunoServiceMock.obterTodos()).willReturn(listaEsperada);

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(ALUNO_PATH)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2))

                .andExpect(jsonPath("[0].id").value(1L))
                .andExpect(jsonPath("[0].nome").value("Maria de Andrade"))
                .andExpect(jsonPath("[0].cpf").value("06065002003"))

                .andExpect(jsonPath("[1].id").value(2L))
                .andExpect(jsonPath("[1].nome").value("João Pedro Lima"))
                .andExpect(jsonPath("[1].cpf").value("16345356000"))
                .andDo(MockMvcResultHandlers.print());
    }

    // -------------------- CONSULTA DE ALUNO -------------------- //

    // DADO: uma requisição
    // QUANDO: não encontrar aluno por id
    // ENTÃO: deve retornar 404
    @DisplayName("Deve retornar 404 quando não encontrar o id")
    @Test
    void deveRetornar404ParaIdNaoEncontrado() throws Exception {
        // given
        Long id = 1L;

        BDDMockito.given(alunoServiceMock.obterPorId(id)).willThrow(new ResourceNotFoundException(id));

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(ALUNO_PATH + "/" + id)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("status").value("404"))
                .andExpect(jsonPath("error").value("Recurso não encontrado"))
                .andExpect(jsonPath("message").value("Recurso não encontrado. Id = " + id))
                .andExpect(jsonPath("path").value(ALUNO_PATH + "/" + id))
                .andDo(MockMvcResultHandlers.print());

       Mockito.verify(alunoServiceMock, Mockito.times(1)).obterPorId(id);
    }

    // DADO: uma requisição
    // QUANDO: encontrar aluno por id
    // ENTÃO: deve retornar 200 e o aluno encontrado
    @DisplayName("Deve retornar 200 e o aluno quando encontrar por id")
    @Test
    void deveRetornar200EAlunoEncontrado() throws Exception {
        // given
        var aluno = new Aluno("Maria de Andrade", "06065002003");
        aluno.setId(1L);

        BDDMockito.given(alunoServiceMock.obterPorId(aluno.getId())).willReturn(aluno);

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(ALUNO_PATH + "/" + aluno.getId())
                .accept(MediaType.APPLICATION_JSON);

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("nome").value("Maria de Andrade"))
                .andExpect(jsonPath("cpf").value("06065002003"))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(alunoServiceMock, Mockito.times(1)).obterPorId(aluno.getId());
    }

    // -------------------- CRIAÇÃO DE ALUNO -------------------- //

    // DADO: uma requisião
    // QUANDO: os dados estiverem incorretos
    // ENTÃO: deve retornar 400 e os campo(s) com erro(s)
    @DisplayName("Deve retornar 400 quando os dados estiverem incorretos")
    @Test
    void deveRetornar400ParaDadosIncorretos() throws Exception {
        // given
        var alunoSemCpf = new Aluno("Leonardo", "");
        var alunoSemNome = new Aluno("", "19037853838");
        var alunoSemNada = new Aluno("", "");

        String content1 = objectMapper.writeValueAsString(alunoSemCpf);
        String content2 = objectMapper.writeValueAsString(alunoSemNome);
        String content3 = objectMapper.writeValueAsString(alunoSemNada);

        // when
        MockHttpServletRequestBuilder request1 = MockMvcRequestBuilders.post(ALUNO_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content1);


        MockHttpServletRequestBuilder request2 = MockMvcRequestBuilders.post(ALUNO_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content2);


        MockHttpServletRequestBuilder request3 = MockMvcRequestBuilders.post(ALUNO_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content3);

        // then
        mockMvc
                .perform(request1)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("400"))
                .andExpect(jsonPath("error").value("Bad Request"))
                .andExpect(jsonPath("message").value("Verifique os campo(s) com erro"))
                .andExpect(jsonPath("path").value(ALUNO_PATH))
                .andExpect(jsonPath("fields").value("cpf"))
                .andExpect(jsonPath("fieldsMessage").value("Cpf inválido"));

        Mockito.verify(alunoServiceMock, Mockito.never()).salvar(any(Aluno.class));

        mockMvc
                .perform(request2)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("400"))
                .andExpect(jsonPath("error").value("Bad Request"))
                .andExpect(jsonPath("message").value("Verifique os campo(s) com erro"))
                .andExpect(jsonPath("path").value(ALUNO_PATH))
                .andExpect(jsonPath("fields").value("nome"))
                .andExpect(jsonPath("fieldsMessage").value("Nome não deve ser vazio ou nulo"));

        Mockito.verify(alunoServiceMock, Mockito.never()).salvar(any(Aluno.class));

        mockMvc
                .perform(request3)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("400"))
                .andExpect(jsonPath("error").value("Bad Request"))
                .andExpect(jsonPath("message").value("Verifique os campo(s) com erro"))
                .andExpect(jsonPath("path").value(ALUNO_PATH))
                .andExpect(jsonPath("fields").value("cpf, nome"))
                .andExpect(jsonPath("fieldsMessage").value("Cpf inválido, " +
                        "Nome não deve ser vazio ou nulo" ))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(alunoServiceMock, Mockito.never()).salvar(any(Aluno.class));
    }

    // DADO: uma requisição
    // QUANDO: o cpf estiver incorreto
    // ENTÃO: deve retornar 400 para cpf inválido
    @DisplayName("Deve retornar 400 quando informar cpf inválido")
    @Test
    void deveRetornar400QuandoInformarCpfInvalido() throws Exception {
        // given
        var alunoComCpfDeDigitosIguais = new Aluno("Maria", "99999999999");
        var alunoComCpfIncorreto = new Aluno("Pedro", "18479021891");
        var alunoComCpfSem11Digitos = new Aluno("João", "3868102000");

        String content1 = objectMapper.writeValueAsString(alunoComCpfDeDigitosIguais);
        String content2 = objectMapper.writeValueAsString(alunoComCpfIncorreto);
        String content3 = objectMapper.writeValueAsString(alunoComCpfSem11Digitos);

        // when
        MockHttpServletRequestBuilder request1 = MockMvcRequestBuilders
                .post(ALUNO_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content1);

        MockHttpServletRequestBuilder request2 = MockMvcRequestBuilders
                .post(ALUNO_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content2);

        MockHttpServletRequestBuilder request3 = MockMvcRequestBuilders
                .post(ALUNO_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content3);

        // then
        // Validando CPF com digitos iguais
        mockMvc
                .perform(request1)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("400"))
                .andExpect(jsonPath("error").value("Bad Request"))
                .andExpect(jsonPath("message").value("Verifique os campo(s) com erro"))
                .andExpect(jsonPath("path").value(ALUNO_PATH))
                .andExpect(jsonPath("fields").value("cpf"))
                .andExpect(jsonPath("fieldsMessage").value("Cpf inválido"));

        Mockito.verify(alunoServiceMock, Mockito.never()).salvar(any(Aluno.class));

        // Validando CPF incorreto (segundo o cálculo para os digitos verificadores)
        mockMvc
                .perform(request2)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("400"))
                .andExpect(jsonPath("error").value("Bad Request"))
                .andExpect(jsonPath("message").value("Verifique os campo(s) com erro"))
                .andExpect(jsonPath("path").value(ALUNO_PATH))
                .andExpect(jsonPath("fields").value("cpf"))
                .andExpect(jsonPath("fieldsMessage").value("Cpf inválido"));

        Mockito.verify(alunoServiceMock, Mockito.never()).salvar(any(Aluno.class));

        // Validando CPF que não contêm 11 digitos
        mockMvc
                .perform(request3)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("400"))
                .andExpect(jsonPath("error").value("Bad Request"))
                .andExpect(jsonPath("message").value("Verifique os campo(s) com erro"))
                .andExpect(jsonPath("path").value(ALUNO_PATH))
                .andExpect(jsonPath("fields").value("cpf"))
                .andExpect(jsonPath("fieldsMessage").value("Cpf inválido"));

        Mockito.verify(alunoServiceMock, Mockito.never()).salvar(any(Aluno.class));
    }

    // DADO: uma requisição
    // QUANDO: informar cpf valido porem ja existente
    // ENTÃO: deve retornar 400 com a mensagem de cpf já existente
    @DisplayName("Deve retornar 400 quando informar cpf já existente")
    @Test
    void deveRetornar400QuandoInformarCpfJaExistente() throws Exception {
        // given
        var aluno = new Aluno("Rodrigo", "17600765896");
        String jsonContent = objectMapper.writeValueAsString(aluno);

        BDDMockito.given(alunoServiceMock.salvar(any(Aluno.class)))
                .willThrow(new BusinessRuleException("Cpf já existente"));

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(ALUNO_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent);

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("status").value("409"))
                .andExpect(jsonPath("error").value("Conflito"))
                .andExpect(jsonPath("message").value("Cpf já existente"))
                .andExpect(jsonPath("path").value(ALUNO_PATH))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(alunoServiceMock, Mockito.times(1)).salvar(any(Aluno.class));
    }

    // DADO: uma requisição
    // QUANDO: os dados estiverem corretos
    // ENTÃO: deve retornar 201 e o aluno criado
    @DisplayName("Deve retornar 201 e o aluno criado para dados corretos")
    @Test
    void deveCriarAlunoComSucesso() throws Exception {
        // given
        var aluno = new Aluno("Marcelo", "13026179825");
        aluno.setId(1L);

        String json = objectMapper.writeValueAsString(aluno);

        BDDMockito.given(alunoServiceMock.salvar(any(Aluno.class)))
                .willReturn(aluno);

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(ALUNO_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("nome").value("Marcelo"))
                .andExpect(jsonPath("cpf").value("13026179825"))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(alunoServiceMock, Mockito.times(1)).salvar(any(Aluno.class));
    }

    // -------------------- ATUALIZAÇÃO DE ALUNO -------------------- //

    // DADO: uma requisição
    // QUANDO: o aluno não for encontrado pelo id
    // ENTÃO: deve retornar 404 e mensagem de recurso não encontrado
    @DisplayName("Deve retornar 404 e mensagem de recurso não encontrado para atualização")
    @Test
    void deveRetornar404QuandoNaoEncontrarAlunoParaAtualizar() throws Exception {
        // given
        Long id = 1L;
        var aluno = new Aluno("Marcos", "19037853838");

        String json = objectMapper.writeValueAsString(aluno);

        BDDMockito.given(alunoServiceMock.atualizar(anyLong(), any(Aluno.class)))
                .willThrow(new ResourceNotFoundException(id));

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(ALUNO_PATH + "/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("status").value("404"))
                .andExpect(jsonPath("error").value("Recurso não encontrado"))
                .andExpect(jsonPath("message").value("Recurso não encontrado. Id = " + id))
                .andExpect(jsonPath("path").value(ALUNO_PATH + "/" + id))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(alunoServiceMock, Mockito.times(1)).atualizar(anyLong(), any(Aluno.class));
    }

    // DADO: uma requisição
    // QUANDO: os dados estiverem incorretos
    // ENTÃO: deve retornar 400 e os campo(s) com erro(s)
    @DisplayName("Deve retornar 404 ao tentar atualizar com dados incorretos")
    @Test
    void deveRetornar404AoTentarAtualizarComDadosIncorretos() throws Exception {
        // given
        var alunoSemCpf = new Aluno("Leonardo", "");
        var alunoSemNome = new Aluno("", "19037853838");
        var alunoSemNada = new Aluno("", "");

        String content1 = objectMapper.writeValueAsString(alunoSemCpf);
        String content2 = objectMapper.writeValueAsString(alunoSemNome);
        String content3 = objectMapper.writeValueAsString(alunoSemNada);

        // when
        MockHttpServletRequestBuilder request1 = MockMvcRequestBuilders.put(ALUNO_PATH + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content1);


        MockHttpServletRequestBuilder request2 = MockMvcRequestBuilders.put(ALUNO_PATH + "/2")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content2);


        MockHttpServletRequestBuilder request3 = MockMvcRequestBuilders.put(ALUNO_PATH + "/3")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content3);

        // then
        mockMvc
                .perform(request1)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("400"))
                .andExpect(jsonPath("error").value("Bad Request"))
                .andExpect(jsonPath("message").value("Verifique os campo(s) com erro"))
                .andExpect(jsonPath("path").value(ALUNO_PATH + "/1"))
                .andExpect(jsonPath("fields").value("cpf"))
                .andExpect(jsonPath("fieldsMessage").value("Cpf inválido"));

        Mockito.verify(alunoServiceMock, Mockito.never()).atualizar(anyLong(), any(Aluno.class));

        mockMvc
                .perform(request2)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("400"))
                .andExpect(jsonPath("error").value("Bad Request"))
                .andExpect(jsonPath("message").value("Verifique os campo(s) com erro"))
                .andExpect(jsonPath("path").value(ALUNO_PATH + "/2"))
                .andExpect(jsonPath("fields").value("nome"))
                .andExpect(jsonPath("fieldsMessage").value("Nome não deve ser vazio ou nulo"));

        Mockito.verify(alunoServiceMock, Mockito.never()).atualizar(anyLong(), any(Aluno.class));

        mockMvc
                .perform(request3)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("400"))
                .andExpect(jsonPath("error").value("Bad Request"))
                .andExpect(jsonPath("message").value("Verifique os campo(s) com erro"))
                .andExpect(jsonPath("path").value(ALUNO_PATH + "/3"))
                .andExpect(jsonPath("fields").value("cpf, nome"))
                .andExpect(jsonPath("fieldsMessage").value("Cpf inválido, " +
                        "Nome não deve ser vazio ou nulo" ))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(alunoServiceMock, Mockito.never()).atualizar(anyLong(), any(Aluno.class));
    }

    // DADO: uma requisição
    // QUANDO: os dados informados estiverem corretos
    // ENTÃO: deve retornar 200 e o aluno atualizado
    @DisplayName("Deve retornar 200 e o aluno atualizado quando suceddo")
    @Test
    void deveRetornar200AoAtualizarComSucesso() throws Exception {
        // given
        var aluno = new Aluno("Pedro", "83648852868");
        aluno.setId(1L);

        String json = objectMapper.writeValueAsString(aluno);

        BDDMockito.given(alunoServiceMock.atualizar(anyLong(), any(Aluno.class))).willReturn(aluno);

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(ALUNO_PATH + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("nome").value("Pedro"))
                .andExpect(jsonPath("cpf").value("83648852868"));
    }

    // -------------------- DELEÇÃO DE ALUNO -------------------- //

    // DADO: uma requisição
    // QUANDO: o aluno não for encontrado pelo id
    // ENTÃO: deve retornar 404 e mensagem de recurso não encontrado
    @DisplayName("Deve retornar 404 e mensagem de recurso não encontrado para deleção")
    @Test
    void deveRetornar404QuandoNaoEncontrarAlunoParaDeletar() throws Exception {
        // given
        Long idInexistente = 1L;

        BDDMockito.given(alunoServiceMock.deletar(idInexistente))
                .willThrow(new ResourceNotFoundException(idInexistente));

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(ALUNO_PATH + "/" + idInexistente)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("status").value("404"))
                .andExpect(jsonPath("error").value("Recurso não encontrado"))
                .andExpect(jsonPath("message").value("Recurso não encontrado. Id = " + idInexistente))
                .andExpect(jsonPath("path").value(ALUNO_PATH + "/" + idInexistente))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(alunoServiceMock, Mockito.times(1)).deletar(idInexistente);
    }

    // DADO: uma requisição
    // QUANDO: existir um aluno para o id
    // ENTÃO: deve retornar 200 ao deletar com sucesso
    @DisplayName("Deve retornar 200 ao deletar com sucesso")
    @Test
    void deveRetornar200AoDeletarComSucesso() throws Exception {
        // given
        Long idExistente = 1L;

        BDDMockito.doNothing().when(alunoServiceMock).deletar(idExistente);

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(ALUNO_PATH + "/" + idExistente)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(alunoServiceMock, Mockito.times(1)).deletar(idExistente);
    }

}