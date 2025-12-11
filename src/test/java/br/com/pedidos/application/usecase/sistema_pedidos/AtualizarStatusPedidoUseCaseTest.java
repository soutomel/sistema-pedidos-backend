package br.com.pedidos.application.usecase.sistema_pedidos;

import br.com.pedidos.sistema_pedidos.application.usecase.AtualizarStatusPedidoUseCase;
import br.com.pedidos.sistema_pedidos.domain.model.*;
import br.com.pedidos.sistema_pedidos.domain.repository.PedidoRepositoryPort;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AtualizarStatusPedidoUseCaseTest {

    @Mock PedidoRepositoryPort repo;

    @InjectMocks
    AtualizarStatusPedidoUseCase useCase;

    @BeforeEach void setup() { MockitoAnnotations.openMocks(this); }

    @Test
    void deveAtualizarStatus() {
        Pedido pedido = new Pedido(1L, UUID.randomUUID(), null, List.of(
                new PedidoItem(new Produto(1L, "x", BigDecimal.TEN, new Categoria(1L)),1,BigDecimal.TEN,"x")
        ), StatusPedido.AGUARDANDO_PAGAMENTO, BigDecimal.TEN);

        when(repo.buscarPorId(1L)).thenReturn(Optional.of(pedido));
        when(repo.salvar(any())).thenReturn(pedido);

        Pedido atualizado = useCase.executar(1L, StatusPedido.RECEBIDO);

        assertEquals(StatusPedido.RECEBIDO, atualizado.getStatus());
    }

    @Test
    void deveFalharQuandoPedidoNaoExiste() {
        when(repo.buscarPorId(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> useCase.executar(1L, StatusPedido.RECEBIDO));
    }
}

