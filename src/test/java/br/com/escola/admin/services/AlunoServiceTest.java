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
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class AlunoServiceTest {

    @InjectMocks
    private AlunoService alunoService;
    @Mock
    private AlunoRepository alunoRepositoryMock;

    @BeforeEach
    void setup() {
        BDDMockito.when(alunoRepositoryMock.findAll()).thenReturn(List.of(
                AlunoCreator.criarAlunoValido()));

        BDDMockito.when(alunoRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(
                Optional.of(AlunoCreator.criarAlunoValido())
        );

        BDDMockito.when(alunoRepositoryMock.save(ArgumentMatchers.any(Aluno.class)))
                .thenReturn(AlunoCreator.criarAlunoValido());

        BDDMockito.doNothing().when(alunoRepositoryMock).delete(ArgumentMatchers.any(Aluno.class));
    }

    @DisplayName("obterTodos deve retornar lista de alunos quando bem sucedido")
    @Test
    void obterTodos_RetornarListaDeAlunos_QuandoBemSucedido() {
        Aluno aluno = AlunoCreator.criarAlunoValido();

        List<Aluno> alunos = alunoService.obterTodos();

        Assertions.assertThat(alunos).isNotEmpty().hasSize(1);
        Assertions.assertThat(alunos.get(0)).isEqualTo(aluno);
    }

    @DisplayName("obterPorId deve retornar aluno quando bem sucedido")
    @Test
    void obterPorId_RetornarAluno_QuandoBemSucedido() {
        Long idEsperado = AlunoCreator.criarAlunoValido().getId();
        Aluno aluno = alunoService.obterPorId(1L);

        Assertions.assertThat(aluno).isNotNull();
        Assertions.assertThat(aluno.getId()).isEqualTo(idEsperado);
    }

    @DisplayName("obterPorId deve lancar ResourceNotFoundException quando nao encontrar aluno")
    @Test
    void obterPorId_LancarResourceNotFoundException_QuandoNaoEncontrarAluno() {
        BDDMockito.when(alunoRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(
                Optional.empty());

        Assertions.assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> alunoService.obterPorId(1L));
    }

    @DisplayName("salvar deve criar aluno quando bem sucedido")
    @Test
    void salvar_CriarAluno_QuandoBemSucedido() {
        Aluno aluno = alunoService.salvar(AlunoCreator.criarAlunoValido());
        Assertions.assertThat(aluno).isNotNull().isEqualTo(AlunoCreator.criarAlunoValido());
    }

    @DisplayName("salvar deve lancar BusinessRuleException quando o cpf ja existir")
    @Test
    void salvar_LancarBusinessRuleException_QuandoCpfJaExistir() {
        BDDMockito.when(alunoRepositoryMock.existsByCpf(ArgumentMatchers.anyString()))
                .thenReturn(true);

        Assertions.assertThatExceptionOfType(BusinessRuleException.class)
                .isThrownBy(() -> alunoService.salvar(AlunoCreator.criarAlunoValido()));
    }

    @DisplayName("atualizar deve retornar aluno atualizado quando bem sucedido")
    @Test
    void atualizar_RetornarAlunoAtualizado_QuandoBemSucedido() {
        Aluno alunoAtualizado = alunoService.atualizar(
                AlunoCreator.criarAlunoValidoParaAtualizar().getId(),
                AlunoCreator.criarAlunoValidoParaAtualizar());

        Assertions.assertThat(alunoAtualizado).isNotNull().isEqualTo(AlunoCreator.criarAlunoValido());
    }

    @DisplayName("atualizar deve lancar ResourceNotFoundException quando nao encontrar aluno")
    @Test
    void atualizar_LancarResourceNotFoundException_QuandoNaoEncontrarAluno() {
        BDDMockito.when(alunoRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> alunoService.atualizar(2L, AlunoCreator.criarAlunoValido()));
    }

    @DisplayName("deletar deve remover aluno quando bem sucedido")
    @Test
    void deletar_RemoverAluno_QuandoBemSucedido() {
        Assertions.assertThatCode(() -> alunoService.deletar(1L)).doesNotThrowAnyException();
    }

    @DisplayName("deletar deve remover aluno quando bem sucedido")
    @Test
    void deletar_LancarResourceNotFoundException_QuandoNaoEncontrarId() {
        BDDMockito.when(alunoRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> alunoService.deletar(1L));
    }

}