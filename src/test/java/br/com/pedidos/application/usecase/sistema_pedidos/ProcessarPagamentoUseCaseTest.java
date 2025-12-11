package br.com.pedidos.application.usecase.sistema_pedidos;

import br.com.pedidos.sistema_pedidos.application.usecase.ProcessarPagamentoUseCase;
import br.com.pedidos.sistema_pedidos.domain.model.*;
import br.com.pedidos.sistema_pedidos.domain.repository.PedidoRepositoryPort;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProcessarPagamentoUseCaseTest {

    @Mock PedidoRepositoryPort repo;

    @InjectMocks
    ProcessarPagamentoUseCase useCase;

    @BeforeEach void setup() { MockitoAnnotations.openMocks(this); }

    @Test
    void deveAprovarPagamento() {
        Pedido pedido = new Pedido(1L, UUID.randomUUID(), null, List.of(
                new PedidoItem(new Produto(1L, "x", BigDecimal.TEN, new Categoria(1L)),1,BigDecimal.TEN,"x")
        ), StatusPedido.AGUARDANDO_PAGAMENTO, BigDecimal.TEN);

        when(repo.buscarPorCodigo(anyString())).thenReturn(Optional.of(pedido));

        boolean resultado = useCase.executar(
                pedido.getCodigo().toString(),
                "approved",
                MetodoPagamento.PIX,
                UUID.randomUUID().toString()
        );

        assertTrue(resultado);
        assertEquals(StatusPedido.RECEBIDO, pedido.getStatus());
    }

    @Test
    void deveRecusarPagamento() {
        Pedido pedido = new Pedido(1L, UUID.randomUUID(), null, List.of(
                new PedidoItem(new Produto(1L,"x",BigDecimal.TEN,new Categoria(1L)),1,BigDecimal.TEN,"x")
        ), StatusPedido.AGUARDANDO_PAGAMENTO, BigDecimal.TEN);

        when(repo.buscarPorCodigo(anyString())).thenReturn(Optional.of(pedido));

        boolean resultado = useCase.executar(
                pedido.getCodigo().toString(),
                "rejected",
                MetodoPagamento.PIX,
                UUID.randomUUID().toString()
        );

        assertFalse(resultado);
        assertEquals(StatusPedido.CANCELADO, pedido.getStatus());
    }
}
