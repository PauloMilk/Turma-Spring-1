package br.com.escola.admin.controllers.diretor;

import br.com.escola.admin.controllers.diretor.DiretorController;
import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.models.Diretor;
import br.com.escola.admin.services.DiretorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
//@AutoConfigureMockMvc
@ActiveProfiles("test")
@WebMvcTest(DiretorController.class)
public class DiretorControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    DiretorService mockService;

    @Test
    @DisplayName("deveCriarDiretor Máscara")
    void deveCriarDiretor() throws Exception {
        //Dado um aluno válido
        Diretor diretor = new Diretor();
        diretor.setCpf("1234");
        diretor.setId(1L);
        diretor.setNome("Rafael");
        String json = new ObjectMapper().writeValueAsString(diretor);

        //Quando fizer uma request
        Mockito.when(mockService.cadastrarDiretor(any())).thenReturn(diretor);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/diretores")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        //Então deve retornar o aluno criado 201.
        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value("Rafael"))
                .andExpect(MockMvcResultMatchers.jsonPath("cpf").value("1234"));
    }

    @Test
    @DisplayName("deveRetornarErroAoCriarDiretorComCpfExistente Máscara")
    void deveRetornarErroAoCriarDiretorComCpfExistente() throws Exception {
        //Dado um aluno válido
        Diretor diretor = new Diretor();
        diretor.setCpf("1234");
        diretor.setId(1L);
        diretor.setNome("Rafael");
        String json = new ObjectMapper().writeValueAsString(diretor);

        //Quando fizer uma request
        Mockito.when(mockService.cadastrarDiretor(any())).thenThrow(new BusinessRuleException("Já existe diretor com esse cpf"));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/diretores")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        //Então deve retornar 400.
        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Já existe diretor com esse cpf"));

    }

    @Test
    void deveRetornarErroAoAtualizarDiretorComCpfExistente() throws Exception {
        //Dado um aluno válido
        Diretor diretor = new Diretor();
        diretor.setCpf("1234");
        diretor.setId(1L);
        diretor.setNome("Rafael");


        String json = new ObjectMapper().writeValueAsString(diretor);


        Mockito.when(mockService.atualizarDiretor(anyLong(), any(Diretor.class))).thenThrow(new BusinessRuleException("Já existe diretor com esse cpf"));


        //Quando fizer uma request
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/diretores/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        //Então deve retornar 400.
        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Já existe diretor com esse cpf"));
    }


    @Test
    void deveRetornar200AoAtualizarDiretor() throws Exception {
        //Dado um aluno válido
        Diretor diretor = new Diretor();
        diretor.setCpf("1234");
        diretor.setId(1L);
        diretor.setNome("Rafael");

        String json = new ObjectMapper().writeValueAsString(diretor);

        Mockito.when(mockService.atualizarDiretor(1L, diretor)).thenReturn(diretor);

        //Quando fizer uma request
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/diretores/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        //Então deve retornar 200.
        mvc.perform(request)
                .andExpect(status().isOk());

    }

    @Test
    void deveRetornar200ComListaVazia() throws Exception {
        // Given
        List<Diretor> listaDiretor = List.of();
        given(mockService.obterDiretores()).willReturn(listaDiretor);
        // When

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/diretores")
                .accept(MediaType.APPLICATION_JSON);

        // Then

        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(0));
    }

    @Test
    void deveRetornar200ComListaNaoVazia() throws Exception {
        // Given
        Diretor diretor = new Diretor();
        diretor.setId(1L);
        diretor.setNome("Paulo");
        List<Diretor> listaDiretor = Arrays.asList(diretor);
        given(mockService.obterDiretores()).willReturn(listaDiretor);
        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/diretores")
                .accept(MediaType.APPLICATION_JSON);
        // Then
        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(1))
                .andExpect(jsonPath("$[0].nome", is(diretor.getNome())));
    }

}
