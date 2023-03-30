package br.com.escola.admin.controllers;

import br.com.escola.admin.controllers.professor.ProfessorController;
import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Professor;
import br.com.escola.admin.services.ProfessorService;
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

@WebMvcTest(controllers = ProfessorController.class)
class ProfessorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfessorService professorService;

    private ObjectMapper objectMapper;

    private static final String PROFESSOR_PATH = "/professores";

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
    }

    // ---------------------- CONSULTAR LISTA DE PROFESSORES ---------------------- //

    // DADO: requisição
    // QUANDO: não houver professores
    // ENTÃO: deve retornar 200 com uma lista vazia
    @DisplayName("Deve retornar 200 quando não houver professores")
    @Test
    void deveRetornar200QuandoNaoHouverProfessores() throws Exception {
        // given
        List<Professor> listaEsperada = List.of();
        BDDMockito.given(professorService.obterTodos()).willReturn(listaEsperada);

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PROFESSOR_PATH)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("length()").value(0));
    }

    // DADO: requisição
    // QUANDO: houver professores
    // ENTÃO: deve retornar 200 com uma lista de professor
    @DisplayName("Deve retornar 200 quando houver professores")
    @Test
    void deveRetornar200QuandoHouverProfessores() throws Exception {
        // given
        var professor1 = new Professor("Maria", "06065002003", "Ciência da Dados");
        professor1.setId(1L);
        var professor2 = new Professor("João", "16345356000", "Engenharia Elétrica");
        professor2.setId(2L);

        List<Professor> listaEsperada = List.of(professor1, professor2);

        BDDMockito.given(professorService.obterTodos()).willReturn(listaEsperada);

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PROFESSOR_PATH)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2))

                .andExpect(jsonPath("[0].id").value(1L))
                .andExpect(jsonPath("[0].nome").value("Maria"))
                .andExpect(jsonPath("[0].cpf").value("06065002003"))
                .andExpect(jsonPath("[0].especialidade").value("Ciência da Dados"))

                .andExpect(jsonPath("[1].id").value(2L))
                .andExpect(jsonPath("[1].nome").value("João"))
                .andExpect(jsonPath("[1].cpf").value("16345356000"))
                .andExpect(jsonPath("[1].especialidade").value("Engenharia Elétrica"))
                .andDo(MockMvcResultHandlers.print());
    }

    // -------------------- CONSULTA DE PROFESSOR -------------------- //

    // DADO: uma requisição
    // QUANDO: não encontrar professor por id
    // ENTÃO: deve retornar 404
    @DisplayName("Deve retornar 404 quando não encontrar o id")
    @Test
    void deveRetornar404ParaIdNaoEncontrado() throws Exception {
        // given
        Long id = 1L;

        BDDMockito.given(professorService.obter(id)).willThrow(new ResourceNotFoundException(id));

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PROFESSOR_PATH + "/" + id)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("status").value("404"))
                .andExpect(jsonPath("error").value("Recurso não encontrado"))
                .andExpect(jsonPath("message").value("Recurso não encontrado. Id = " + id))
                .andExpect(jsonPath("path").value(PROFESSOR_PATH + "/" + id))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(professorService, Mockito.times(1)).obter(id);
    }

    // DADO: uma requisição
    // QUANDO: encontrar professor por id
    // ENTÃO: deve retornar 200 e o professor encontrado
    @DisplayName("Deve retornar 200 e o professor quando encontrar por id")
    @Test
    void deveRetornar200EProfessorEncontrado() throws Exception {
        // given
        var professor = new Professor("Maria", "06065002003", "Ciência da Dados");
        professor.setId(1L);

        BDDMockito.given(professorService.obter(professor.getId())).willReturn(professor);

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PROFESSOR_PATH + "/" + professor.getId())
                .accept(MediaType.APPLICATION_JSON);

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("nome").value("Maria"))
                .andExpect(jsonPath("cpf").value("06065002003"))
                .andExpect(jsonPath("especialidade").value("Ciência da Dados"))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(professorService, Mockito.times(1)).obter(professor.getId());
    }

    // -------------------- CRIAÇÃO DE PROFESSOR -------------------- //

    // DADO: uma requisião
    // QUANDO: os dados estiverem incorretos
    // ENTÃO: deve retornar 400 e os campo(s) com erro(s)
    @DisplayName("Deve retornar 400 quando os dados estiverem incorretos")
    @Test
    void deveRetornar400ParaDadosIncorretos() throws Exception {
        // given
        var professorSemCpf = new Professor("Leonardo", "", "Engenharia de Software");
        var professorSemNome = new Professor("", "19037853838", "Engenharia Elétrica");
        var professorSemNada = new Professor("", "", "");

        String content1 = objectMapper.writeValueAsString(professorSemCpf);
        String content2 = objectMapper.writeValueAsString(professorSemNome);
        String content3 = objectMapper.writeValueAsString(professorSemNada);

        // when
        MockHttpServletRequestBuilder request1 = MockMvcRequestBuilders.post(PROFESSOR_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content1);


        MockHttpServletRequestBuilder request2 = MockMvcRequestBuilders.post(PROFESSOR_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content2);


        MockHttpServletRequestBuilder request3 = MockMvcRequestBuilders.post(PROFESSOR_PATH)
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
                .andExpect(jsonPath("path").value(PROFESSOR_PATH))
                .andExpect(jsonPath("fields").value("cpf"))
                .andExpect(jsonPath("fieldsMessage").value("Cpf inválido"));

        Mockito.verify(professorService, Mockito.never()).salvar(any(Professor.class));

        mockMvc
                .perform(request2)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("400"))
                .andExpect(jsonPath("error").value("Bad Request"))
                .andExpect(jsonPath("message").value("Verifique os campo(s) com erro"))
                .andExpect(jsonPath("path").value(PROFESSOR_PATH))
                .andExpect(jsonPath("fields").value("nome"))
                .andExpect(jsonPath("fieldsMessage").value("Nome não deve ser vazio ou nulo"));

        Mockito.verify(professorService, Mockito.never()).salvar(any(Professor.class));

        mockMvc
                .perform(request3)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("400"))
                .andExpect(jsonPath("error").value("Bad Request"))
                .andExpect(jsonPath("message").value("Verifique os campo(s) com erro"))
                .andExpect(jsonPath("path").value(PROFESSOR_PATH))
                .andExpect(jsonPath("fields").value("cpf, especialidade, nome"))
                .andExpect(jsonPath("fieldsMessage").value(
                        "Cpf inválido, " +
                        "Especialidade não deve ser vazio ou nulo, " +
                        "Nome não deve ser vazio ou nulo" ))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(professorService, Mockito.never()).salvar(any(Professor.class));
    }

    // DADO: uma requisição
    // QUANDO: o cpf estiver incorreto
    // ENTÃO: deve retornar 400 para cpf inválido
    @DisplayName("Deve retornar 400 quando informar cpf inválido")
    @Test
    void deveRetornar400QuandoInformarCpfInvalido() throws Exception {
        // given
        var professorComCpfDeDigitosIguais = new Professor("Maria", "99999999999", "Engenharia de Software");
        var professorComCpfIncorreto = new Professor("Pedro", "18479021891", "Engenharia Elétrica");
        var professorComCpfSem11Digitos = new Professor("João", "3868102000", "Engenharia de Produção");

        String content1 = objectMapper.writeValueAsString(professorComCpfDeDigitosIguais);
        String content2 = objectMapper.writeValueAsString(professorComCpfIncorreto);
        String content3 = objectMapper.writeValueAsString(professorComCpfSem11Digitos);

        // when
        MockHttpServletRequestBuilder request1 = MockMvcRequestBuilders
                .post(PROFESSOR_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content1);

        MockHttpServletRequestBuilder request2 = MockMvcRequestBuilders
                .post(PROFESSOR_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content2);

        MockHttpServletRequestBuilder request3 = MockMvcRequestBuilders
                .post(PROFESSOR_PATH)
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
                .andExpect(jsonPath("path").value(PROFESSOR_PATH))
                .andExpect(jsonPath("fields").value("cpf"))
                .andExpect(jsonPath("fieldsMessage").value("Cpf inválido"));

        Mockito.verify(professorService, Mockito.never()).salvar(any(Professor.class));

        // Validando CPF incorreto (segundo o cálculo para os digitos verificadores)
        mockMvc
                .perform(request2)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("400"))
                .andExpect(jsonPath("error").value("Bad Request"))
                .andExpect(jsonPath("message").value("Verifique os campo(s) com erro"))
                .andExpect(jsonPath("path").value(PROFESSOR_PATH))
                .andExpect(jsonPath("fields").value("cpf"))
                .andExpect(jsonPath("fieldsMessage").value("Cpf inválido"));

        Mockito.verify(professorService, Mockito.never()).salvar(any(Professor.class));

        // Validando CPF que não contêm 11 digitos
        mockMvc
                .perform(request3)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("400"))
                .andExpect(jsonPath("error").value("Bad Request"))
                .andExpect(jsonPath("message").value("Verifique os campo(s) com erro"))
                .andExpect(jsonPath("path").value(PROFESSOR_PATH))
                .andExpect(jsonPath("fields").value("cpf"))
                .andExpect(jsonPath("fieldsMessage").value("Cpf inválido"));

        Mockito.verify(professorService, Mockito.never()).salvar(any(Professor.class));
    }

    // DADO: uma requisição
    // QUANDO: informar cpf valido porem ja existente
    // ENTÃO: deve retornar 400 com a mensagem de cpf já existente
    @DisplayName("Deve retornar 400 quando informar cpf já existente")
    @Test
    void deveRetornar400QuandoInformarCpfJaExistente() throws Exception {
        // given
        var professor = new Professor("Rodrigo", "17600765896", "Engenharia de Software");
        String jsonContent = objectMapper.writeValueAsString(professor);

        BDDMockito.given(professorService.salvar(any(Professor.class)))
                .willThrow(new BusinessRuleException("Cpf já existente"));

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(PROFESSOR_PATH)
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
                .andExpect(jsonPath("path").value(PROFESSOR_PATH))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(professorService, Mockito.times(1)).salvar(any(Professor.class));
    }

    // DADO: uma requisição
    // QUANDO: os dados estiverem corretos
    // ENTÃO: deve retornar 201 e o professor criado
    @DisplayName("Deve retornar 201 e o professor criado para dados corretos")
    @Test
    void deveCriarProfessorComSucesso() throws Exception {
        // given
        var professor = new Professor("Marcelo", "13026179825", "Engenharia de Software");
        professor.setId(1L);

        String json = objectMapper.writeValueAsString(professor);

        BDDMockito.given(professorService.salvar(any(Professor.class)))
                .willReturn(professor);

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(PROFESSOR_PATH)
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
                .andExpect(jsonPath("especialidade").value("Engenharia de Software"))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(professorService, Mockito.times(1)).salvar(any(Professor.class));
    }

    // -------------------- ATUALIZAÇÃO DE PROFESSOR -------------------- //

    // DADO: uma requisição
    // QUANDO: o professor não for encontrado pelo id
    // ENTÃO: deve retornar 404 e mensagem de recurso não encontrado
    @DisplayName("Deve retornar 404 e mensagem de recurso não encontrado para atualização")
    @Test
    void deveRetornar404QuandoNaoEncontrarProfessorParaAtualizar() throws Exception {
        // given
        Long id = 1L;
        var professor = new Professor("Marcos", "19037853838", "Engenhaira de Software");

        String json = objectMapper.writeValueAsString(professor);

        BDDMockito.given(professorService.atualizar(anyLong(), any(Professor.class)))
                .willThrow(new ResourceNotFoundException(id));

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(PROFESSOR_PATH + "/" + id)
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
                .andExpect(jsonPath("path").value(PROFESSOR_PATH + "/" + id))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(professorService, Mockito.times(1)).atualizar(anyLong(), any(Professor.class));
    }

    // DADO: uma requisição
    // QUANDO: os dados estiverem incorretos
    // ENTÃO: deve retornar 400 e os campo(s) com erro(s)
    @DisplayName("Deve retornar 404 ao tentar atualizar com dados incorretos")
    @Test
    void deveRetornar404AoTentarAtualizarComDadosIncorretos() throws Exception {
        // given
        var professorSemCpf = new Professor("Leonardo", "", "Engenharia de Software");
        var professorSemNome = new Professor("", "19037853838", "Engenharia Elétrica");
        var professorSemNada = new Professor("", "", "");

        String content1 = objectMapper.writeValueAsString(professorSemCpf);
        String content2 = objectMapper.writeValueAsString(professorSemNome);
        String content3 = objectMapper.writeValueAsString(professorSemNada);

        // when
        MockHttpServletRequestBuilder request1 = MockMvcRequestBuilders.put(PROFESSOR_PATH + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content1);


        MockHttpServletRequestBuilder request2 = MockMvcRequestBuilders.put(PROFESSOR_PATH + "/2")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content2);


        MockHttpServletRequestBuilder request3 = MockMvcRequestBuilders.put(PROFESSOR_PATH + "/3")
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
                .andExpect(jsonPath("path").value(PROFESSOR_PATH + "/1"))
                .andExpect(jsonPath("fields").value("cpf"))
                .andExpect(jsonPath("fieldsMessage").value("Cpf inválido"));

        Mockito.verify(professorService, Mockito.never()).atualizar(anyLong(), any(Professor.class));

        mockMvc
                .perform(request2)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("400"))
                .andExpect(jsonPath("error").value("Bad Request"))
                .andExpect(jsonPath("message").value("Verifique os campo(s) com erro"))
                .andExpect(jsonPath("path").value(PROFESSOR_PATH + "/2"))
                .andExpect(jsonPath("fields").value("nome"))
                .andExpect(jsonPath("fieldsMessage").value("Nome não deve ser vazio ou nulo"));

        Mockito.verify(professorService, Mockito.never()).atualizar(anyLong(), any(Professor.class));

        mockMvc
                .perform(request3)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("400"))
                .andExpect(jsonPath("error").value("Bad Request"))
                .andExpect(jsonPath("message").value("Verifique os campo(s) com erro"))
                .andExpect(jsonPath("path").value(PROFESSOR_PATH + "/3"))
                .andExpect(jsonPath("fields").value("cpf, especialidade, nome"))
                .andExpect(jsonPath("fieldsMessage").value(
                        "Cpf inválido, " +
                        "Especialidade não deve ser vazio ou nulo, " +
                        "Nome não deve ser vazio ou nulo"))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(professorService, Mockito.never()).atualizar(anyLong(), any(Professor.class));
    }

    // DADO: uma requisição
    // QUANDO: os dados informados estiverem corretos
    // ENTÃO: deve retornar 200 e o professor atualizado
    @DisplayName("Deve retornar 200 e o professor atualizado quando suceddo")
    @Test
    void deveRetornar200AoAtualizarComSucesso() throws Exception {
        // given
        var professor = new Professor("Pedro", "83648852868", "Engenharia de Produção");
        professor.setId(1L);

        String json = objectMapper.writeValueAsString(professor);

        BDDMockito.given(professorService.atualizar(anyLong(), any(Professor.class))).willReturn(professor);

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(PROFESSOR_PATH + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("nome").value("Pedro"))
                .andExpect(jsonPath("cpf").value("83648852868"))
                .andExpect(jsonPath("especialidade").value("Engenharia de Produção"));
    }

    // -------------------- DELEÇÃO DE PROFESSOR -------------------- //

    // DADO: uma requisição
    // QUANDO: o professor não for encontrado pelo id
    // ENTÃO: deve retornar 404 e mensagem de recurso não encontrado
    @DisplayName("Deve retornar 404 e mensagem de recurso não encontrado para deleção")
    @Test
    void deveRetornar404QuandoNaoEncontrarProfessorParaDeletar() throws Exception {
        // given
        Long idInexistente = 1L;

        BDDMockito.given(professorService.deletar(idInexistente))
                .willThrow(new ResourceNotFoundException(idInexistente));

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(PROFESSOR_PATH + "/" + idInexistente)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("status").value("404"))
                .andExpect(jsonPath("error").value("Recurso não encontrado"))
                .andExpect(jsonPath("message").value("Recurso não encontrado. Id = " + idInexistente))
                .andExpect(jsonPath("path").value(PROFESSOR_PATH + "/" + idInexistente))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(professorService, Mockito.times(1)).deletar(idInexistente);
    }

    // DADO: uma requisição
    // QUANDO: existir um professor para o id
    // ENTÃO: deve retornar 200 ao deletar com sucesso
    @DisplayName("Deve retornar 200 ao deletar com sucesso")
    @Test
    void deveRetornar200AoDeletarComSucesso() throws Exception {
        // given
        Long idExistente = 1L;

        BDDMockito.doNothing().when(professorService).deletar(idExistente);

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(PROFESSOR_PATH + "/" + idExistente)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mockMvc
                .perform(request)
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(professorService, Mockito.times(1)).deletar(idExistente);
    }

}