package br.com.pedidos.sistema_pedidos.application.usecase;

import br.com.pedidos.sistema_pedidos.domain.model.Pedido;
import br.com.pedidos.sistema_pedidos.domain.model.StatusPedido;
import br.com.pedidos.sistema_pedidos.domain.repository.PedidoRepositoryPort;

public class AtualizarStatusPedidoUseCase {
    private final PedidoRepositoryPort pedidoRepositoryPort;

    public AtualizarStatusPedidoUseCase(PedidoRepositoryPort pedidoRepositoryPort) {
        this.pedidoRepositoryPort = pedidoRepositoryPort;
    }

    public Pedido executar(Long pedidoId, StatusPedido novoStatus) {

        Pedido pedido = pedidoRepositoryPort.buscarPorId(pedidoId)
                .orElseThrow(() -> new NotFoundException("Pedido", pedidoId));

        if (novoStatus == StatusPedido.RECEBIDO) {
            pedido.marcarComoRecebido();
        } else {
            pedido.setStatus(novoStatus); // Para simplificar, permitimos outras transições
        }

        return pedidoRepositoryPort.salvar(pedido);
    }
}
