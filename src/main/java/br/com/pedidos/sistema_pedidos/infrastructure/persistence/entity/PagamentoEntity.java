package br.com.pedidos.sistema_pedidos.infrastructure.persistence.entity;

import br.com.pedidos.sistema_pedidos.domain.model.Pagamento.StatusPagamento; // Enum do Domínio
import jakarta.persistence.*;

@Entity
@Table(name = "pagamentos")
public class PagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento: Um Pagamento para Um Pedido (usando Pedido ID como Foreign Key)
    // Mapeamos a coluna 'pedido_id' que também será a chave única (Unique=true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", unique = true, nullable = false)
    private PedidoEntity pedido;

    private String metodoPagamento;

    @Enumerated(EnumType.STRING)
    private StatusPagamento status;

    // Construtores e Getters/Setters
    public PagamentoEntity() {}

    public PagamentoEntity(PedidoEntity pedido, String metodoPagamento, StatusPagamento status) {
        this.pedido = pedido;
        this.metodoPagamento = metodoPagamento;
        this.status = status;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public PedidoEntity getPedido() { return pedido; }
    public String getMetodoPagamento() { return metodoPagamento; }
    public StatusPagamento getStatus() { return status; }

    public void setId(Long id) { this.id = id; }
    public void setPedido(PedidoEntity pedido) { this.pedido = pedido; }
    public void setMetodoPagamento(String metodoPagamento) { this.metodoPagamento = metodoPagamento; }
    public void setStatus(StatusPagamento status) { this.status = status; }
}