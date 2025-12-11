package br.com.pedidos.application.usecase.sistema_pedidos;

import br.com.pedidos.sistema_pedidos.application.usecase.CriarPedidoUseCase;
import br.com.pedidos.sistema_pedidos.domain.model.*;
import br.com.pedidos.sistema_pedidos.domain.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CriarPedidoUseCaseTest {

    @Mock
    private PedidoRepositoryPort pedidoRepo;

    @Mock
    private ClienteRepositoryPort clienteRepo;

    @Mock
    private ProdutoRepositoryPort produtoRepo;

    @InjectMocks
    private CriarPedidoUseCase useCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarPedidoComItensSimples() {

        // --- Comando de entrada ---
        var command = new CriarPedidoUseCase.CriarPedidoCommand(
                "12345678901",
                List.of(new CriarPedidoUseCase.ItemCommand(1L, 2))
        );

        // --- Mock do cliente --
        Cliente cliente = new Cliente("12345678901", "Ana", "ana@mail.com");
        when(clienteRepo.buscarPorCpf("12345678901"))
                .thenReturn(Optional.of(cliente));


        // --- Mock do produto ---
        Produto produto = new Produto(1L, "Coca", BigDecimal.TEN, new Categoria(1L, "Bebida"));
        when(produtoRepo.buscarPorId(1L)).thenReturn(Optional.of(produto));

        // --- Mock do salvar ---
        when(pedidoRepo.salvar(any())).thenAnswer(inv -> inv.getArgument(0));

        // --- Execução ---
        Pedido pedido = useCase.executar(command);

        // --- Verificações ---
        assertNotNull(pedido);
        assertEquals(1, pedido.getItens().size());
        assertEquals(BigDecimal.valueOf(20), pedido.getValorTotal());
    }

    @Test
    void deveFalharQuandoNaoHaItens() {

        var command = new CriarPedidoUseCase.CriarPedidoCommand(
                "12345678901",
                List.of() // sem itens
        );

        assertThrows(IllegalArgumentException.class, () -> useCase.executar(command));
    }
}
