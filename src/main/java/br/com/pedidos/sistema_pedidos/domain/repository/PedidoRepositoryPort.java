package br.com.pedidos.sistema_pedidos.domain.repository;

import br.com.pedidos.sistema_pedidos.domain.model.Pedido;
import br.com.pedidos.sistema_pedidos.domain.model.StatusPedido;
import java.util.List;
import java.util.Optional;

public interface PedidoRepositoryPort {

    Pedido salvar(Pedido pedido);
    Optional<Pedido> buscarPorId(Long id);

    // <<< NOVO MÉTODO NECESSÁRIO PARA O SPRINT 4 >>>
    List<Pedido> buscarPorStatusEmAndamento();

    // <<< NOVO MÉTODO NECESSÁRIO PARA O WEBHOOK/PAGAMENTO (SPRINT 5) >>>
    Optional<Pedido> buscarPorCodigo(String codigo);
}