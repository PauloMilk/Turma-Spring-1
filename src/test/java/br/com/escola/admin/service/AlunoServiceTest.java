package br.com.escola.admin.service;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Aluno;
import br.com.escola.admin.repositories.AlunoRepository;
import br.com.escola.admin.services.AlunoService;
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
public class AlunoServiceTest {
    @Mock
    AlunoRepository mockRepository;

    @InjectMocks
    AlunoService alunoService;

    @Test
    public void  deveRetornarErroQuandoConsultarAlunoPorCpfInexistente(){
        //BDD
        //given
        String cpf = "12345";
        //when
        Throwable exception = Assertions.catchThrowable(() -> alunoService.findByCpf(cpf));
        //then
        Assertions.assertThat(exception)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Aluno não encontrado para o CPF: " + cpf);
    }

    @Test
    public void deveRetornarAlunoAoConsultarCpf(){
        //given
        Aluno alunoMock = alunoMock();
        //when
        when(mockRepository.findByCpf(any())).thenReturn(alunoMock);
        Aluno aluno = alunoService.findByCpf(alunoMock().getCpf());
        //then
        assertNotNull(aluno);
        assertEquals(alunoMock().getCpf(), aluno.getCpf());
        assertEquals("Rafael", aluno.getNome());
    }

    @Test
    public void deveRetornarErroQuandoDeletarAlunoInexistente(){
        Long id = 1L;
        //when
        Throwable exception = Assertions.catchThrowable(() -> alunoService.deletarAluno(id));
        //then
        Assertions.assertThat(exception)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Não existe aluno com esse ID");
    }

    @Test
    public void deveDeletarUmAluno(){
        // given
        Aluno aluno = alunoMock();
        // salva o aluno no mock de repository
        when(mockRepository.findById(1L)).thenReturn(Optional.of(aluno));

        // when
        alunoService.deletarAluno(1L);

        // then
        verify(mockRepository, times(1)).delete(aluno);
    }

    @Test
    public void deveRetornarErroAoAtualizarUmAlunoComCpfRepetido() {
        // given
        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setCpf("1234");
        Aluno aluno = alunoMock();
        when(mockRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(mockRepository.findByCpf("1234")).thenReturn(aluno);
        when(mockRepository.findById(2L)).thenReturn(Optional.of(aluno2));

        // when
        Throwable exception = Assertions.catchThrowable(() -> alunoService.atualizarAluno(2L, aluno2));


        // then
        Assertions.assertThat(exception)
                .isInstanceOf(BusinessRuleException.class)
                .hasMessage("Já existe aluno com esse CPF");

    }
    @Test
    public void deveAtualizarUmAluno() {
        // given
        Aluno aluno = alunoMock();
        when(mockRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(mockRepository.findByCpf("1234")).thenReturn(aluno);

        // when

        Aluno alunoAtualizado = alunoService.obterAlunoPorId(1L);
        alunoAtualizado.setNome("João");
        alunoAtualizado.setCpf("1234");
        alunoService.atualizarAluno(1L, alunoAtualizado);

        // then
        assertEquals(aluno.getNome(),"João");


    }



    private Aluno alunoMock(){
        Aluno alunoMock = new Aluno();
        alunoMock.setCpf("1234");
        alunoMock.setId(1L);
        alunoMock.setNome("Rafael");
        return alunoMock;
    }
}
