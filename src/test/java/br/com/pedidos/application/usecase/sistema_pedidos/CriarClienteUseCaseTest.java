package br.com.pedidos.application.usecase.sistema_pedidos;

import br.com.pedidos.sistema_pedidos.application.usecase.CriarClienteUseCase;
import br.com.pedidos.sistema_pedidos.domain.model.Cliente;
import br.com.pedidos.sistema_pedidos.domain.repository.ClienteRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CriarClienteUseCaseTest {

    @Mock
    private ClienteRepositoryPort repo;

    @InjectMocks
    private CriarClienteUseCase useCase;

    @BeforeEach
    void setup() { MockitoAnnotations.openMocks(this); }

    @Test
    void deveRetornarClienteExistente() {
        Cliente existente = new Cliente("12345678901", "Ana", "ana@x.com");

        when(repo.buscarPorCpf("12345678901")).thenReturn(Optional.of(existente));

        Cliente resultado = useCase.executar(existente);

        assertEquals("Ana", resultado.getNome());
        verify(repo, never()).salvar(any());
    }

    @Test
    void deveCriarClienteNovo() {
        Cliente novo = new Cliente("12345678901", "Ana", "ana@x.com");

        when(repo.buscarPorCpf("12345678901")).thenReturn(Optional.empty());
        when(repo.salvar(any())).thenReturn(novo);

        Cliente resultado = useCase.executar(novo);

        assertEquals("Ana", resultado.getNome());
        verify(repo).salvar(any());
    }
}

