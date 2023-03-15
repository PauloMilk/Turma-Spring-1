package br.com.escola.admin.service;

import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Diretor;
import br.com.escola.admin.repositories.DiretorRepository;
import br.com.escola.admin.services.AlunoService;
import br.com.escola.admin.services.DiretorService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class DiretorServiceTest {
    @MockBean
    DiretorRepository mockRepository;
    DiretorService diretorService;

    @BeforeEach
    public void setUp(){
        diretorService = new DiretorService();
    }
    @Test
    public void  deveRetornarErroQuandoConsultarDiretorPorCpfInexistente(){
        //BDD
        //given
        String cpf = "12345";
        //when
        Throwable exception = Assertions.catchThrowable(() -> diretorService.findByCpf(cpf));
        //then
        Assertions.assertThat(exception)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Diretor não encontrado para o CPF: " + cpf);
    }

    public void deveRetornarDiretorAoConsultarCpf(){
        //BDD
        //TDD - Teste antes de desenvolver
        //given
        String cpf = "1234";
        Diretor diretorMock = new Diretor();
        diretorMock.setCpf("1234");
        diretorMock.setId(1L);
        diretorMock.setNome("Rafael");
        //when
        Mockito.when(mockRepository.findByCpf(any())).thenReturn(diretorMock);
        Diretor diretor = diretorService.findByCpf(cpf);
        //then
        assertNotNull(diretor);
        assertEquals(cpf, diretor.getCpf());
        assertEquals("Rafael", diretor.getNome());
    }
}
