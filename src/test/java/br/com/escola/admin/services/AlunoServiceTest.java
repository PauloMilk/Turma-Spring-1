package br.com.escola.admin.services;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Aluno;
import br.com.escola.admin.repositories.AlunoRepository;
import br.com.escola.admin.utils.AlunoCreator;
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
class AlunoServiceTest {

    @InjectMocks
    private AlunoService alunoService;
    @Mock
    private AlunoRepository alunoRepositoryMock;

    @BeforeEach
    void setup() {
    }

    // --------------------- CONSULTAR ALUNOS --------------------- //

    // DADO: uma consulta de alunos
    // QUANDO: não encontrar alunos
    // ENTÃO: deve retornar lista vazia
    @DisplayName("Deve retornar lista vazia ao não encontrar alunos")
    @Test
    void deveRetornarListaVaziaQuandoNaoEncontrarAlunos() {
        // given
        List<Aluno> listaEsperada = List.of();

        // when
        BDDMockito.when(alunoRepositoryMock.findAll()).thenReturn(listaEsperada);

        // then
        List<Aluno> listaRetornada = alunoService.obterTodos();

        Assertions.assertThatList(listaRetornada).isEmpty();
        Mockito.verify(alunoRepositoryMock, Mockito.times(1)).findAll();
    }

    // DADO: uma consutla de alunos
    // QUANDO: encontrar alunos
    // ENTÃO: deve retornar a lista com alunos
    @DisplayName("Deve retornar uma lista de aluno quando encontrar alunos")
    @Test
    void deveRetornarUmaListaDeAlunoComSucesso() {
        // given
        var aluno1 = new Aluno("Maria", "44420047062");
        aluno1.setId(1L);
        var aluno2 = new Aluno("Pedro", "10030358094");
        aluno2.setId(2L);

        List<Aluno> listaEsperada = List.of(aluno1, aluno2);

        // when
        BDDMockito.when(alunoRepositoryMock.findAll()).thenReturn(listaEsperada);

        // then
        List<Aluno> listaRetornada = alunoService.obterTodos();

        Assertions.assertThatList(listaRetornada).hasSize(2);
        Assertions.assertThat(listaRetornada.get(0)).isEqualTo(aluno1);
        Assertions.assertThat(listaRetornada.get(1)).isEqualTo(aluno2);

        Mockito.verify(alunoRepositoryMock, Mockito.times(1)).findAll();
    }

    // --------------------- CONSULTAR ALUNO --------------------- //

    // DADO: um id inexistente
    // QUANDO: consultar aluno pelo id
    // ENTÃO: deve lançar ResourceNotFoundException
    @DisplayName("Deve lançar ResourceNotFoundException quando não encontrar aluno")
    @Test
    void deveLancarErroQuandoNaoEncontrarAluno() {
        // given
        Long idInexistente = 1L;

        // when
        BDDMockito.when(alunoRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        Throwable exception = Assertions.catchThrowable(() -> alunoService.obterPorId(idInexistente));

        // then
        Assertions.assertThat(exception)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Recurso não encontrado. Id = " + idInexistente);

        Mockito.verify(alunoRepositoryMock, Mockito.times(1)).findById(anyLong());
    }

    // DADO: id existente
    // QUANDO: consultar aluno pelo id
    // ENTÃO: deve retornar o aluno encontrado
    @DisplayName("Deve retornar aluno com sucesso")
    @Test
    void deveRetornarAlunoComSucesso() {
        // given
        var alunoEsperado = new Aluno("Maria", "93747777031");
        alunoEsperado.setId(1L);

        // when
        BDDMockito.when(alunoRepositoryMock.findById(anyLong())).thenReturn(Optional.of(alunoEsperado));

        // then
        Aluno alunoRetornado = alunoService.obterPorId(alunoEsperado.getId());

        Assertions.assertThat(alunoRetornado).isNotNull();
        Assertions.assertThat(alunoEsperado.getId()).isEqualTo(alunoRetornado.getId());
        Assertions.assertThat(alunoEsperado.getNome()).isEqualTo(alunoRetornado.getNome());
        Assertions.assertThat(alunoEsperado.getCpf()).isEqualTo(alunoRetornado.getCpf());

        Mockito.verify(alunoRepositoryMock, Mockito.times(1)).findById(anyLong());
    }

    // --------------------- CRIAR ALUNO --------------------- //

    // DADO: um cpf existente
    // QUANDO: tentar criar aluno
    // ENTÃO: deve lançar BusinessRuleException
    @DisplayName("Deve lançar BusinessRuleException ao tentar criar aluno com cpf existente")
    @Test
    void deveLancarErroAoTentarCriarAlunoParaUmCpfExistente() {
        // given
        var aluno = new Aluno("Marcelo", "93747777031");

        // when
        BDDMockito.when(alunoRepositoryMock.existsByCpf(anyString())).thenReturn(true);
        Throwable exception = Assertions.catchThrowable(() -> alunoService.salvar(aluno));

        // then
        Assertions.assertThat(exception)
                .isInstanceOf(BusinessRuleException.class)
                .hasMessage("Já existe um aluno com esse cpf");

        Mockito.verify(alunoRepositoryMock, Mockito.times(1)).existsByCpf(anyString());

    }

    // DADO: um cpf inexistente
    // QUANDO: tentar criar aluno
    // ENTÃO: deve retornar aluno criado
    @DisplayName("Deve retornar o aluno criado com sucesso")
    @Test
    void deveRetornarAlunoCriadoComSucesso() {
        // given
        var alunoEsperado = new Aluno("Marcelo", "93747777031");

        // when
        BDDMockito.when(alunoRepositoryMock.save(any(Aluno.class))).thenReturn(alunoEsperado);

        // then
        Aluno alunoSalvo = alunoService.salvar(alunoEsperado);

        Assertions.assertThat(alunoSalvo).isNotNull();
        Assertions.assertThat(alunoSalvo.getNome()).isEqualTo(alunoEsperado.getNome());
        Assertions.assertThat(alunoSalvo.getCpf()).isEqualTo(alunoEsperado.getCpf());

        Mockito.verify(alunoRepositoryMock, Mockito.times(1)).existsByCpf(anyString());
        Mockito.verify(alunoRepositoryMock, Mockito.times(1)).save(any(Aluno.class));
    }

    // --------------------- ATUALIZAÇÃO DE ALUNO --------------------- //

    // DADO: um id inexistente
    // QUANDO: tentar atualizar aluno
    // ENTÃO: deve lançar ResourceNotFoundException
    @DisplayName("Deve lançar ResourceNotFoundException ao tentar atualizar aluno para id inexistente")
    @Test
    void deveLancarErroQuandoTentarAtualizarAluno() {
        // given
        var aluno = new Aluno("Marcelo", "93747777031");
        aluno.setId(1L);

        // when
        BDDMockito.when(alunoRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = Assertions.catchException(() -> alunoService.atualizar(aluno.getId(), aluno));

        // then
        Assertions.assertThat(exception)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Recurso não encontrado. Id = " + aluno.getId());

        Mockito.verify(alunoRepositoryMock, Mockito.times(1)).findById(anyLong());
        Mockito.verify(alunoRepositoryMock, Mockito.never()).save(any(Aluno.class));
    }

    // DAOD: um id existente
    // QUANDO: tentar atualizar aluno
    // ENTÃO: deve retornar aluno atualizado
    @DisplayName("Deve retornar aluno atualizado com sucesso")
    @Test
    void deveRetornarAlunoAtualizadoComSucesso() {
        // given
        var alunoParaAtualizar = new Aluno("Marcelo", "93747777031");
        alunoParaAtualizar.setId(1L);

        // when
        BDDMockito.when(alunoRepositoryMock.findById(anyLong())).thenReturn(Optional.of(alunoParaAtualizar));
        BDDMockito.when(alunoRepositoryMock.save(any(Aluno.class))).thenReturn(alunoParaAtualizar);

        // then
        Aluno alunoAtualizado = alunoService.atualizar(alunoParaAtualizar.getId(), alunoParaAtualizar);

        Assertions.assertThat(alunoAtualizado).isNotNull();
        Assertions.assertThat(alunoAtualizado.getId()).isEqualTo(alunoParaAtualizar.getId());
        Assertions.assertThat(alunoAtualizado.getNome()).isEqualTo(alunoParaAtualizar.getNome());
        Assertions.assertThat(alunoAtualizado.getCpf()).isEqualTo(alunoParaAtualizar.getCpf());

        Mockito.verify(alunoRepositoryMock, Mockito.times(1)).findById(anyLong());
        Mockito.verify(alunoRepositoryMock, Mockito.times(1)).save(any(Aluno.class));
    }

    // --------------------- DELEÇÃO DE ALUNO --------------------- //

    // DADO: um id inexistente
    // QUANDO: tentar deletar aluno
    // ENTÃO: deve lançar ResourceNotFoundException
    @DisplayName("Deve lançar ResourceNotFoundException ao tentar deletar aluno para id inexistente")
    @Test
    void deveLancarErroAoTentarDeletarAluno() {
        // given
        Long idInexistente = 1L;

        // when
        BDDMockito.when(alunoRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = Assertions.catchException(() -> alunoService.deletar(idInexistente));

        // then
        Assertions.assertThat(exception)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Recurso não encontrado. Id = " + idInexistente);

        Mockito.verify(alunoRepositoryMock, Mockito.times(1)).findById(anyLong());
        Mockito.verify(alunoRepositoryMock, Mockito.never()).delete(any(Aluno.class));
    }

    // DADO: um id existente
    // QUANDO: tentar deletar aluno
    // ENTÃO: deve deletar com sucesso
    @DisplayName("Deve deletar um aluno com sucesso")
    @Test
    void deveDeletarAlunoComSucesso() {
        // given
        var alunoParaDeletar = new Aluno("Rodrigo", "93747777031");
        alunoParaDeletar.setId(1L);

        // when
        BDDMockito.when(alunoRepositoryMock.findById(anyLong())).thenReturn(Optional.of(alunoParaDeletar));
        BDDMockito.doNothing().when(alunoRepositoryMock).delete(any(Aluno.class));

        // then
        Assertions.assertThatCode(() -> alunoService.deletar(alunoParaDeletar.getId()))
                .doesNotThrowAnyException();

        Mockito.verify(alunoRepositoryMock, Mockito.times(1)).findById(anyLong());
        Mockito.verify(alunoRepositoryMock, Mockito.times(1)).delete(any(Aluno.class));
    }
}