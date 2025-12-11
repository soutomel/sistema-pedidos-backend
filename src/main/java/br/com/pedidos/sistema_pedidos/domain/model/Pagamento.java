package br.com.pedidos.sistema_pedidos.domain.model;

import java.util.UUID; // Import necessário para transacaoId

public class Pagamento {

    public enum StatusPagamento { APROVADO, RECUSADO, PENDENTE }

    private Long id;
    private final Pedido pedido;

    // <<< MUDANÇA 1: Troca de String para Enum tipado >>>
    private final MetodoPagamento metodoPagamento;

    // <<< MUDANÇA 2: ID externo da transação (para webhooks e rastreamento) >>>
    private final UUID transacaoId;

    private StatusPagamento status;

    // Construtor 1: Completo para criação (chamado pelo UseCase)
    public Pagamento(Pedido pedido, MetodoPagamento metodoPagamento, UUID transacaoId) {
        if (pedido == null || metodoPagamento == null || transacaoId == null) {
            throw new IllegalArgumentException("Pedido, método e ID de transação são obrigatórios.");
        }
        this.pedido = pedido;
        this.metodoPagamento = metodoPagamento;
        this.transacaoId = transacaoId;
        this.status = StatusPagamento.PENDENTE;
    }

    // Métodos de Negócio
    public void aprovar() {
        this.status = StatusPagamento.APROVADO;
    }

    // NOVO MÉTODO: Recusar
    public void recusar() {
        this.status = StatusPagamento.RECUSADO;
    }

    // Getters
    public Long getId() { return id; }
    public Pedido getPedido() { return pedido; }
    public MetodoPagamento getMetodoPagamento() { return metodoPagamento; } // Getter atualizado
    public UUID getTransacaoId() { return transacaoId; } // Novo Getter
    public StatusPagamento getStatus() { return status; }

    // Setter de ID
    public void setId(Long id) { this.id = id; }
}