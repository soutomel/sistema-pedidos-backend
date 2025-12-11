package br.com.pedidos.sistema_pedidos.application.usecase;

import br.com.pedidos.sistema_pedidos.domain.model.Pedido;
import br.com.pedidos.sistema_pedidos.domain.model.Pagamento; // Novo Import
import br.com.pedidos.sistema_pedidos.domain.model.MetodoPagamento; // Novo Import
import br.com.pedidos.sistema_pedidos.domain.model.StatusPedido;
import br.com.pedidos.sistema_pedidos.domain.repository.PedidoRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

// Reutilizando a exceção já definida
class NotFoundException extends RuntimeException {
    public NotFoundException(String recurso, String codigo) {
        super(recurso + " com Código/UUID " + codigo + " não encontrado.");
    }
    public NotFoundException(String recurso, Long id) {
        super(recurso + " com ID " + id + " não encontrado.");
    }
}

@Service
public class ProcessarPagamentoUseCase {

    private final PedidoRepositoryPort pedidoRepositoryPort;

    public ProcessarPagamentoUseCase(PedidoRepositoryPort pedidoRepositoryPort) {
        this.pedidoRepositoryPort = pedidoRepositoryPort;
    }

    /**
     * Processa a notificação de pagamento, seja via Webhook ou Fila.
     * @param codigoPedido O código UUID do pedido.
     * @param statusGateway O status retornado pelo Mercado Pago (ex: "approved", "rejected").
     * @param metodo Metodo de pagamento (PIX, CREDITO, DEBITO).
     * @param transacaoId ID da transação no Gateway.
     */
    public boolean executar(String codigoPedido, String statusGateway, MetodoPagamento metodo, String transacaoId) {

        // 1. Busca o pedido pelo Código (UUID)
        Pedido pedido = pedidoRepositoryPort.buscarPorCodigo(codigoPedido)
                .orElseThrow(() -> new NotFoundException("Pedido", codigoPedido));

        // 2. Lógica de decisão
        if ("approved".equalsIgnoreCase(statusGateway)) {

            // 3. Cria a Entidade Pagamento (Agora usa o Enum MetodoPagamento)
            Pagamento novoPagamento = new Pagamento(
                    pedido,
                    metodo,
                    UUID.fromString(transacaoId)
            );

            // 4. Marca como aprovado e muda o status do pedido (Domínio)
            novoPagamento.aprovar();
            pedido.marcarComoRecebido();

            // OBS: O PedidoEntity precisará de um campo para Pagamento (OneToOne)
            // e a lógica de salvamento precisará ser refeita para Pagamento.

            // 5. Salva o pedido (agora com Pagamento aprovado)
            pedidoRepositoryPort.salvar(pedido);
            return true;

        } else if ("rejected".equalsIgnoreCase(statusGateway)) {

            // 6. Pagamento recusado: Muda status no domínio (se for permitido)
            pedido.setStatus(StatusPedido.CANCELADO);
            pedidoRepositoryPort.salvar(pedido);
            return false;

        } else {
            // Outros status (pending, etc.)
            return false;
        }
    }
}