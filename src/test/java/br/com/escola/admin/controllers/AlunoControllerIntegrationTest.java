package br.com.escola.admin.controllers;


import br.com.escola.admin.models.Aluno;
import br.com.escola.admin.repositories.AlunoRepository;
import br.com.escola.admin.repositories.JpaAlunoRepository;
import br.com.escola.admin.services.AlunoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AlunoControllerIntegrationTest {
    private static final String ALUNO_PATH = "/alunos";

    @Autowired
    MockMvc mvc;

    @Autowired
    JpaAlunoRepository jpaAlunoRepository;


    @BeforeEach
    void setUp() {
        List<Aluno> listaEsperada = List.of(
                new Aluno("João da Silva", "12345678900"),
                new Aluno("Maria da Silva", "12345678901")
        );
        jpaAlunoRepository.saveAll(listaEsperada);
    }

    // DADO: uma request
    // QUANDO: houver alunos
    // ENTÃO: deve retornar 200 com uma lista dos alunos
    @Test
    @DisplayName("Deve retornar 200 quando houver alunos")
    void deveRetornar200QuandoHouverAlunos() throws Exception {
        // Given

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
}
