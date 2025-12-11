package br.com.pedidos.sistema_pedidos.application.usecase; // <<< ESTA LINHA É CRUCIAL

import br.com.pedidos.sistema_pedidos.domain.model.Pedido;
import br.com.pedidos.sistema_pedidos.domain.repository.PedidoRepositoryPort;
import java.util.List;

public class BuscarPedidosEmAndamentoUseCase {

    private final PedidoRepositoryPort pedidoRepositoryPort;

    public BuscarPedidosEmAndamentoUseCase(PedidoRepositoryPort pedidoRepositoryPort) {
        this.pedidoRepositoryPort = pedidoRepositoryPort;
    }

    public List<Pedido> executar() {
        return pedidoRepositoryPort.buscarPorStatusEmAndamento();
    }
}
