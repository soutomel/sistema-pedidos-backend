package br.com.pedidos.sistema_pedidos.infrastructure.persistence.entity;

import br.com.pedidos.sistema_pedidos.domain.model.StatusPedido; // Enum do Domínio
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pedidos")
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID codigo; // UUID gerado no Domínio

    // Relacionamento: Muitos Pedidos para Um Cliente (usando CPF como FK)
    @ManyToOne(fetch = FetchType.LAZY)
    // CORRIGIDO: Referencia a coluna 'cpf' na tabela 'clientes'
    @JoinColumn(name = "cliente_cpf", referencedColumnName = "cpf", nullable = false)
    private ClienteEntity cliente;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @OneToMany(
            mappedBy = "pedido",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<PedidoItemEntity> itens;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PagamentoEntity pagamento;

    // Construtores e Getters/Setters
    public PedidoEntity() {}

    public PedidoEntity(UUID codigo, ClienteEntity cliente, List<PedidoItemEntity> itens, StatusPedido status, BigDecimal valorTotal) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.status = status;
        this.valorTotal = valorTotal;
        this.itens = itens;

        if (this.itens != null) {
            this.itens.forEach(item -> item.setPedido(this));
        }
    }

    // Getters e Setters (necessários para o JPA)
    public Long getId() { return id; }
    public UUID getCodigo() { return codigo; }
    public ClienteEntity getCliente() { return cliente; }
    public List<PedidoItemEntity> getItens() { return itens; }
    public StatusPedido getStatus() { return status; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public PagamentoEntity getPagamento() { return pagamento; }

    public void setId(Long id) { this.id = id; }
    public void setStatus(StatusPedido status) { this.status = status; }
    public void setItens(List<PedidoItemEntity> itens) {
        this.itens = itens;
        if (this.itens != null) {
            this.itens.forEach(item -> item.setPedido(this));
        }
    }
    public void setPagamento(PagamentoEntity pagamento) { this.pagamento = pagamento; }
}