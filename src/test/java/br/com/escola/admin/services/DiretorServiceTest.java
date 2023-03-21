package br.com.escola.admin.services;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Diretor;
import br.com.escola.admin.repositories.DiretorRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class DiretorServiceTest {

    @InjectMocks
    private DiretorService diretorService;

    @Mock
    private DiretorRepository diretorRepositoryMock;

    @BeforeEach
    void setup() {

    }

    // -------------------- CONSULTAR DIRETORES -------------------- //

    // DAOD: uma consulta de diretores
    // QUANDO: não houver diretores
    // ENTÃO: deve retornar lista vazia
    @DisplayName("Deve retornar lista vazia quando não encontrar diretores")
    @Test
    void deveRetornarListaVaziaDeDiretor() {
        // given
        List<Diretor> listaEsperada = List.of();

        // when
        when(diretorRepositoryMock.findAll()).thenReturn(listaEsperada);

        // then
        List<Diretor> listaRetornada = diretorService.obterTodos();

        assertThat(listaRetornada).isEmpty();
        verify(diretorRepositoryMock, times(1)).findAll();
    }

    // DAOD: uma consulta de diretores
    // QUANDO: houver diretores
    // ENTÃO: deve retornar lista de diretor
    @DisplayName("Deve retornar lista de diretor quando encontrar diretores")
    @Test
    void deveRetornarListaDeDiretorComSucesso() {
        // given
        var diretor1 = new Diretor("Marcos", "68508967098");
        diretor1.setId(1L);
        var diretor2 = new Diretor("Luisa", "95181376096");
        diretor2.setId(2L);

        List<Diretor> listaEsperada = List.of(diretor1, diretor2);

        // when
        when(diretorRepositoryMock.findAll()).thenReturn(listaEsperada);

        // then
        List<Diretor> listaRetornada = diretorService.obterTodos();

        assertThat(listaRetornada).isNotEmpty().hasSize(2);
        assertThat(listaRetornada.get(0)).isEqualTo(diretor1);
        assertThat(listaRetornada.get(1)).isEqualTo(diretor2);

        verify(diretorRepositoryMock, times(1)).findAll();
    }

    // -------------------- CONSULTAR DIRETOR -------------------- //

    // DAOD: um id inexistente
    // QUANDO: consultar diretor
    // ENTÃO: deve lançar ResourceNotFoundException ao não encontrar
    @DisplayName("Deve lançar ResourceNotFoundException ao não encontrar diretor")
    @Test
    void deveLancarErroQuandoNaoEncontrarDiretor() {
        // given
        Long idInexistente = 1L;

        // when
        when(diretorRepositoryMock.findById(anyLong()))
                .thenThrow(new ResourceNotFoundException(idInexistente));

        Exception exception = Assertions.catchException(() -> diretorService.obter(idInexistente));

        // then
        assertThat(exception)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Recurso não encontrado. Id = " + idInexistente);

        verify(diretorRepositoryMock, times(1)).findById(anyLong());
    }

    // DAOD: um id existente
    // QUANDO: consultar diretor
    // ENTÃO: deve retornar o diretor encontrado
    @DisplayName("Deve retornar diretor encontrado com sucesso")
    @Test
    void deveRetornarDiretorComSucesso() {
        // given
        var diretorEsperado = new Diretor("Marcos", "68508967098");
        diretorEsperado.setId(1L);

        // when
        when(diretorRepositoryMock.findById(anyLong())).thenReturn(Optional.of(diretorEsperado));

        // then
        Diretor diretorRetornado = diretorService.obter(diretorEsperado.getId());

        assertThat(diretorRetornado).isNotNull();
        assertThat(diretorRetornado.getId()).isEqualTo(diretorEsperado.getId());
        assertThat(diretorRetornado.getNome()).isEqualTo(diretorEsperado.getNome());
        assertThat(diretorRetornado.getCpf()).isEqualTo(diretorEsperado.getCpf());

        verify(diretorRepositoryMock, times(1)).findById(anyLong());
    }

    // -------------------- CRIAÇÃO DE DIRETOR -------------------- //

    // DADO: um cpf existente
    // QUANDO: tentar criar diretor
    // ENTÃO: deve lançar BusinessRuleException como erro
    @DisplayName("Deve lançar BusinessRuleException quando já existir um cpf")
    @Test
    void deveLancarErroAoTentarCriarDiretor() {
        // given
        var diretor = new Diretor("Marcos", "68508967098");

        // when
        when(diretorRepositoryMock.existsByCpf(anyString())).thenReturn(true);
        Exception exception = Assertions.catchException(() -> diretorService.criar(diretor));

        // then
        assertThat(exception)
                .isInstanceOf(BusinessRuleException.class)
                .hasMessage("Já existe um diretor para esse cpf");

        verify(diretorRepositoryMock, times(1)).existsByCpf(anyString());
        verify(diretorRepositoryMock, Mockito.never()).save(any(Diretor.class));
    }

    // DADO: um cpf inexistente
    // QUANDO: tentar criar diretor
    // ENTÃO: deve retornar o diretor criado com sucesso
    @DisplayName("Deve retornar diretor criado com sucesso")
    @Test
    void deveRetornarDiretorCriadoComSucesso() {
        // given
        var diretorParaSalvar = new Diretor("Marcos", "68508967098");
        diretorParaSalvar.setId(1L);

        given(diretorRepositoryMock.existsByCpf(anyString())).willReturn(false);

        // when
        when(diretorRepositoryMock.save(any(Diretor.class))).thenReturn(diretorParaSalvar);

        // then
        Diretor diretorSalvo = diretorService.criar(diretorParaSalvar);

        assertThat(diretorSalvo).isNotNull();
        assertThat(diretorSalvo.getId()).isEqualTo(diretorParaSalvar.getId());
        assertThat(diretorSalvo.getNome()).isEqualTo(diretorParaSalvar.getNome());
        assertThat(diretorSalvo.getCpf()).isEqualTo(diretorParaSalvar.getCpf());

        verify(diretorRepositoryMock, times(1)).existsByCpf(anyString());
        verify(diretorRepositoryMock, times(1)).save(any(Diretor.class));
    }

    // -------------------- ATUALIZAÇÃO DE DIRETOR -------------------- //

    // DAOD: um id inexistente
    // QUANDO: tentar atualizar diretor
    // ENTÃO: deve lançar ResourceNotFoundException com erro
    @DisplayName("Deve lançar ResourceNotFoundException quando não encontrar diretor para atualizar")
    @Test
    void deveLancarErroAoTentarAtualizarDiretor() {
        // given
        var diretor = new Diretor("Marcos", "68508967098");
        diretor.setId(1L);

        // when
        when(diretorRepositoryMock.existsById(anyLong())).thenReturn(false);
        Exception exception = catchException(() -> diretorService.atualizar(diretor.getId(), diretor));

        // then
        assertThat(exception)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Recurso não encontrado. Id = " + diretor.getId());

        verify(diretorRepositoryMock, times(1)).existsById(anyLong());
        verify(diretorRepositoryMock, never()).save(any(Diretor.class));
    }

    // DAOD: um id existente
    // QUANDO: tentar atualizar diretor
    // ENTÃO: deve retornar diretor atualizado
    @DisplayName("Deve retornar diretor atualizado com sucesso")
    @Test
    void deveRetornarDiretorAtualizadoComSucesso() {
        // given
        var diretorParaAtualizar = new Diretor("Marcos", "68508967098");
        diretorParaAtualizar.setId(1L);

        given(diretorRepositoryMock.existsById(anyLong())).willReturn(true);

        // when
        when(diretorRepositoryMock.save(any(Diretor.class))).thenReturn(diretorParaAtualizar);

        // then
        Diretor diretorAtualizado = diretorService.atualizar(diretorParaAtualizar.getId(), diretorParaAtualizar);

        assertThat(diretorAtualizado).isNotNull();
        assertThat(diretorAtualizado.getId()).isEqualTo(diretorParaAtualizar.getId());
        assertThat(diretorAtualizado.getNome()).isEqualTo(diretorParaAtualizar.getNome());
        assertThat(diretorAtualizado.getCpf()).isEqualTo(diretorParaAtualizar.getCpf());

        verify(diretorRepositoryMock, times(1)).existsById(anyLong());
        verify(diretorRepositoryMock, times(1)).save(any(Diretor.class));
    }

    // -------------------- DELEÇÃO DE DIRETOR -------------------- //

    // DAOD: um id inexistente
    // QUANDO: tentar deletar diretor
    // ENTÃO: deve lançar ResourceNotFoundException com erro
    @DisplayName("Deve lançar ResourceNotFoundException quando não encontrar diretor para deletar")
    @Test
    void deveLancarErroQuandoTentarDeletarDiretor() {
        // given
        Long idInexistente = 1L;

        // when
        when(diretorRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = catchException(() -> diretorService.deletar(idInexistente));

        // then
        assertThat(exception)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Recurso não encontrado. Id = " + idInexistente);

        verify(diretorRepositoryMock, times(1)).findById(anyLong());
        verify(diretorRepositoryMock, never()).delete(any(Diretor.class));
    }

    // DAOD: um id existente
    // QUANDO: tentar deletar diretor
    // ENTÃO: deve deleter diretor com sucesso
    @DisplayName("Deve deletar diretor com sucesso")
    @Test
    void deveDeletarDiretorcomSucesso() {
        // given
        var diretor = new Diretor("Marcos", "68508967098");
        diretor.setId(1L);

        given(diretorRepositoryMock.findById(anyLong())).willReturn(Optional.of(diretor));

        // when
        doNothing().when(diretorRepositoryMock).delete(any(Diretor.class));

        // then
        assertThatCode(() -> diretorService.deletar(diretor.getId())).doesNotThrowAnyException();

        verify(diretorRepositoryMock, times(1)).findById(anyLong());
        verify(diretorRepositoryMock, times(1)).delete(any(Diretor.class));
    }

}