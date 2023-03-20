package br.com.escola.admin.services;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Professor;
import br.com.escola.admin.repositories.ProfessorRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
class ProfessorServiceTest {

    @InjectMocks
    private ProfessorService professorService;
    @Mock
    private ProfessorRepository professorRepositoryMock;

    @BeforeEach
    void setup() {
    }

    // --------------------- CONSULTAR PROFESSORES --------------------- //

    // DADO: uma consulta de professores
    // QUANDO: não encontrar professores
    // ENTÃO: deve retornar lista vazia
    @DisplayName("Deve retornar lista vazia ao não encontrar professores")
    @Test
    void deveRetornarListaVaziaQuandoNaoEncontrarProfessores() {
        // given
        List<Professor> listaEsperada = List.of();

        // when
        BDDMockito.when(professorRepositoryMock.findAll()).thenReturn(listaEsperada);

        // then
        List<Professor> listaRetornada = professorService.obterTodos();

        Assertions.assertThatList(listaRetornada).isEmpty();
        Mockito.verify(professorRepositoryMock, Mockito.times(1)).findAll();
    }

    // DADO: uma consulta de professores
    // QUANDO: encontrar professores
    // ENTÃO: deve retornar a lista com professores
    @DisplayName("Deve retornar uma lista de professor quando encontrar professores")
    @Test
    void deveRetornarUmaListaDeProfessorComSucesso() {
        // given
        var professor1 = new Professor("Maria", "44420047062", "Engenharia de Software");
        professor1.setId(1L);
        var professor2 = new Professor("Pedro", "10030358094", "Engenhearia de Produção");
        professor2.setId(2L);

        List<Professor> listaEsperada = List.of(professor1, professor2);

        // when
        BDDMockito.when(professorRepositoryMock.findAll()).thenReturn(listaEsperada);

        // then
        List<Professor> listaRetornada = professorService.obterTodos();

        Assertions.assertThatList(listaRetornada).hasSize(2);
        Assertions.assertThat(listaRetornada.get(0)).isEqualTo(professor1);
        Assertions.assertThat(listaRetornada.get(1)).isEqualTo(professor2);

        Mockito.verify(professorRepositoryMock, Mockito.times(1)).findAll();
    }

    // --------------------- CONSULTAR PROFESSOR --------------------- //

    // DADO: um id inexistente
    // QUANDO: consultar professor pelo id
    // ENTÃO: deve lançar ResourceNotFoundException
    @DisplayName("Deve lançar ResourceNotFoundException quando não encontrar professor")
    @Test
    void deveLancarErroQuandoNaoEncontrarProfessor() {
        // given
        Long idInexistente = 1L;

        // when
        BDDMockito.when(professorRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        Throwable exception = Assertions.catchThrowable(() -> professorService.obter(idInexistente));

        // then
        Assertions.assertThat(exception)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Recurso não encontrado. Id = " + idInexistente);

        Mockito.verify(professorRepositoryMock, Mockito.times(1)).findById(anyLong());
    }

    // DADO: id existente
    // QUANDO: consultar professor pelo id
    // ENTÃO: deve retornar o professor encontrado
    @DisplayName("Deve retornar professor com sucesso")
    @Test
    void deveRetornarProfessorComSucesso() {
        // given
        var professorEsperado = new Professor("Maria", "93747777031", "Ciência de Dados");
        professorEsperado.setId(1L);

        // when
        BDDMockito.when(professorRepositoryMock.findById(anyLong())).thenReturn(Optional.of(professorEsperado));

        // then
        Professor professorRetornado = professorService.obter(professorEsperado.getId());

        Assertions.assertThat(professorRetornado).isNotNull();
        Assertions.assertThat(professorEsperado.getId()).isEqualTo(professorRetornado.getId());
        Assertions.assertThat(professorEsperado.getNome()).isEqualTo(professorRetornado.getNome());
        Assertions.assertThat(professorEsperado.getCpf()).isEqualTo(professorRetornado.getCpf());
        Assertions.assertThat(professorEsperado.getEspecialidade()).isEqualTo(professorRetornado.getEspecialidade());

        Mockito.verify(professorRepositoryMock, Mockito.times(1)).findById(anyLong());
    }

    // --------------------- CRIAR PROFESSOR --------------------- //

    // DADO: um cpf existente
    // QUANDO: tentar criar professor
    // ENTÃO: deve lançar BusinessRuleException
    @DisplayName("Deve lançar BusinessRuleException ao tentar criar professor com cpf existente")
    @Test
    void deveLancarErroAoTentarCriarProfessorParaUmCpfExistente() {
        // given
        var professor = new Professor("Marcelo", "93747777031", "Engenharia de Software");

        // when
        BDDMockito.when(professorRepositoryMock.existsByCpf(anyString())).thenReturn(true);
        Throwable exception = Assertions.catchThrowable(() -> professorService.salvar(professor));

        // then
        Assertions.assertThat(exception)
                .isInstanceOf(BusinessRuleException.class)
                .hasMessage("Ja existe um professor com esse cpf");

        Mockito.verify(professorRepositoryMock, Mockito.times(1)).existsByCpf(anyString());
    }

    // DADO: um cpf inexistente
    // QUANDO: tentar criar professor
    // ENTÃO: deve retornar professor criado
    @DisplayName("Deve retornar o professor criado com sucesso")
    @Test
    void deveRetornarProfessorCriadoComSucesso() {
        // given
        var professorEsperado = new Professor("Marcelo", "93747777031", "Engenharia de Softwsare");

        // when
        BDDMockito.when(professorRepositoryMock.save(any(Professor.class))).thenReturn(professorEsperado);

        // then
        Professor professorSalvo = professorService.salvar(professorEsperado);

        Assertions.assertThat(professorSalvo).isNotNull();
        Assertions.assertThat(professorSalvo.getNome()).isEqualTo(professorEsperado.getNome());
        Assertions.assertThat(professorSalvo.getCpf()).isEqualTo(professorEsperado.getCpf());
        Assertions.assertThat(professorSalvo.getEspecialidade()).isEqualTo(professorEsperado.getEspecialidade());

        Mockito.verify(professorRepositoryMock, Mockito.times(1)).existsByCpf(anyString());
        Mockito.verify(professorRepositoryMock, Mockito.times(1)).save(any(Professor.class));
    }

    // --------------------- ATUALIZAÇÃO DE PROFESSOR --------------------- //

    // DADO: um id inexistente
    // QUANDO: tentar atualizar professor
    // ENTÃO: deve lançar ResourceNotFoundException
    @DisplayName("Deve lançar ResourceNotFoundException ao tentar atualizar professor para id inexistente")
    @Test
    void deveLancarErroQuandoTentarAtualizarProfessor() {
        // given
        var professor = new Professor("Marcelo", "93747777031", "Engenharia de Software");
        professor.setId(1L);

        // when
        BDDMockito.when(professorRepositoryMock.existsById(anyLong())).thenReturn(false);
        Exception exception = Assertions.catchException(() -> professorService.atualizar(professor.getId(), professor));

        // then
        Assertions.assertThat(exception)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Recurso não encontrado. Id = " + professor.getId());

        Mockito.verify(professorRepositoryMock, Mockito.times(1)).existsById(anyLong());
        Mockito.verify(professorRepositoryMock, Mockito.never()).save(any(Professor.class));
    }

    // DAOD: um id existente
    // QUANDO: tentar atualizar professor
    // ENTÃO: deve retornar professor atualizado
    @DisplayName("Deve retornar professor atualizado com sucesso")
    @Test
    void deveRetornarProfessorAtualizadoComSucesso() {
        // given
        var professorParaAtualizar = new Professor("Marcelo", "93747777031", "Engenharia de Software");
        professorParaAtualizar.setId(1L);

        // when
        BDDMockito.when(professorRepositoryMock.existsById(anyLong())).thenReturn(true);
        BDDMockito.when(professorRepositoryMock.save(any(Professor.class))).thenReturn(professorParaAtualizar);

        // then
        Professor professorAtualizado = professorService.atualizar(professorParaAtualizar.getId(), professorParaAtualizar);

        Assertions.assertThat(professorAtualizado).isNotNull();
        Assertions.assertThat(professorAtualizado.getId()).isEqualTo(professorParaAtualizar.getId());
        Assertions.assertThat(professorAtualizado.getNome()).isEqualTo(professorParaAtualizar.getNome());
        Assertions.assertThat(professorAtualizado.getCpf()).isEqualTo(professorParaAtualizar.getCpf());
        Assertions.assertThat(professorAtualizado.getEspecialidade()).isEqualTo(professorParaAtualizar.getEspecialidade());

        Mockito.verify(professorRepositoryMock, Mockito.times(1)).existsById(anyLong());
        Mockito.verify(professorRepositoryMock, Mockito.times(1)).save(any(Professor.class));
    }

    // --------------------- DELEÇÃO DE PROFESSOR --------------------- //

    // DADO: um id inexistente
    // QUANDO: tentar deletar professor
    // ENTÃO: deve lançar ResourceNotFoundException
    @DisplayName("Deve lançar ResourceNotFoundException ao tentar deletar professor para id inexistente")
    @Test
    void deveLancarErroAoTentarDeletarProfessor() {
        // given
        Long idInexistente = 1L;

        // when
        BDDMockito.when(professorRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = Assertions.catchException(() -> professorService.deletar(idInexistente));

        // then
        Assertions.assertThat(exception)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Recurso não encontrado. Id = " + idInexistente);

        Mockito.verify(professorRepositoryMock, Mockito.times(1)).findById(anyLong());
        Mockito.verify(professorRepositoryMock, Mockito.never()).delete(any(Professor.class));
    }

    // DADO: um id existente
    // QUANDO: tentar deletar professor
    // ENTÃO: deve deletar com sucesso
    @DisplayName("Deve deletar um professor com sucesso")
    @Test
    void deveDeletarProfessorComSucesso() {
        // given
        var professorParaDeletar = new Professor("Rodrigo", "93747777031", "Inteligência Artificial");
        professorParaDeletar.setId(1L);

        // when
        BDDMockito.when(professorRepositoryMock.findById(anyLong())).thenReturn(Optional.of(professorParaDeletar));
        BDDMockito.doNothing().when(professorRepositoryMock).delete(any(Professor.class));

        // then
        Assertions.assertThatCode(() -> professorService.deletar(professorParaDeletar.getId()))
                .doesNotThrowAnyException();

        Mockito.verify(professorRepositoryMock, Mockito.times(1)).findById(anyLong());
        Mockito.verify(professorRepositoryMock, Mockito.times(1)).delete(any(Professor.class));
    }

}