package br.com.escola.admin.service;

import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Diretor;
import br.com.escola.admin.repositories.DiretorRepository;
import br.com.escola.admin.services.DiretorService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class DiretorServiceTest {
    @Mock
    DiretorRepository mockRepository;

    @InjectMocks
    DiretorService diretorService;

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

    @Test
    public void deveRetornarDiretorAoConsultarCpf(){
        //BDD
        //TDD - Teste antes de desenvolver
        //given
        String cpf = "1234";
        Diretor diretorMock = diretorMock();

        //when
        when(mockRepository.findByCpf(any())).thenReturn(diretorMock);
        Diretor diretor = diretorService.findByCpf(cpf);
        //then
        assertNotNull(diretor);
        assertEquals(cpf, diretor.getCpf());
        assertEquals("Rafael", diretor.getNome());
    }

    @Test
    public void deveRetornarErroQuandoDeletarDiretorInexistente(){
        //BDD
        //given
        Long id = 1L;
        //when
        Throwable exception = Assertions.catchThrowable(() -> diretorService.deletarDiretor(id));
        //then
        Assertions.assertThat(exception)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Não existe diretor com esse ID");
    }

    @Test
    public void deveDeletarUmDiretor(){
        // given
        Diretor diretor = diretorMock();
        // salva o diretor no mock de repository
        when(mockRepository.findById(1L)).thenReturn(Optional.of(diretor));

        // when
        diretorService.deletarDiretor(1L);

        // then
        verify(mockRepository, times(1)).delete(diretor);
    }


    private Diretor diretorMock(){
        Diretor diretorMock = new Diretor();
        diretorMock.setCpf("1234");
        diretorMock.setId(1L);
        diretorMock.setNome("Rafael");
        return diretorMock;
    }
}
