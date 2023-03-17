package br.com.escola.admin.service;

import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Professor;
import br.com.escola.admin.repositories.ProfessorRepository;
import br.com.escola.admin.services.ProfessorService;
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
public class ProfessorServiceTest {
    @Mock
    ProfessorRepository mockRepository;

    @InjectMocks
    ProfessorService professorService;

    @Test
    public void  deveRetornarErroQuandoConsultarProfessorPorCpfInexistente(){
        //BDD
        //given
        String cpf = "12345";
        //when
        Throwable exception = Assertions.catchThrowable(() -> professorService.findByCpf(cpf));
        //then
        Assertions.assertThat(exception)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Professor não encontrado para o CPF: " + cpf);
    }

    @Test
    public void deveRetornarProfessorAoConsultarCpf(){
        //BDD
        //TDD - Teste antes de desenvolver
        //given
        String cpf = "1234";
        Professor professorMock = professorMock();

        //when
        when(mockRepository.findByCpf(any())).thenReturn(professorMock);
        Professor professor = professorService.findByCpf(cpf);
        //then
        assertNotNull(professor);
        assertEquals(cpf, professor.getCpf());
        assertEquals("Rafael", professor.getNome());
    }

    @Test
    public void deveRetornarErroQuandoDeletarProfessorInexistente(){
        //BDD
        //given
        Long id = 1L;
        //when
        Throwable exception = Assertions.catchThrowable(() -> professorService.deletarProfessor(id));
        //then
        Assertions.assertThat(exception)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Não existe professor com esse ID");
    }

    @Test
    public void deveDeletarUmProfessor(){
        // given
        Professor professor = professorMock();
        // salva o professor no mock de repository
        when(mockRepository.findById(1L)).thenReturn(Optional.of(professor));

        // when
        professorService.deletarProfessor(1L);

        // then
        verify(mockRepository, times(1)).delete(professor);
    }


    private Professor professorMock(){
        Professor professorMock = new Professor();
        professorMock.setCpf("1234");
        professorMock.setId(1L);
        professorMock.setNome("Rafael");
        return professorMock;
    }
}
