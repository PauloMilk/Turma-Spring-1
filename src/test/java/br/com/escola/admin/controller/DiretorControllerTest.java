package br.com.escola.admin.controller;

import br.com.escola.admin.controllers.DiretorController;
import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.models.Diretor;
import br.com.escola.admin.services.AlunoService;
import br.com.escola.admin.services.DiretorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.print.attribute.standard.Media;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
//@AutoConfigureMockMvc
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
}
