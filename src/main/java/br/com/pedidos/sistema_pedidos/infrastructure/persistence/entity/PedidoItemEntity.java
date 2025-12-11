package br.com.pedidos.sistema_pedidos.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pedidos_itens")
public class PedidoItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento: Muitos Itens para Um Produto (do catálogo)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produto_id", nullable = false)
    private ProdutoEntity produto;

    // NOVO CAMPO: Nome do produto fixado no pedido
    @Column(name = "nome_produto", nullable = false)
    private String nomeProduto;

    private int quantidade;

    // O preço é fixado no item e não é pego da tabela 'produtos'
    @Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitario;

    // Relacionamento de volta para o Pedido (opcional para JPA, mas bom para a modelagem)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private PedidoEntity pedido;

    // Construtores e Getters/Setters (Obrigatórios pelo JPA)
    public PedidoItemEntity() {}

    // CONSTRUTOR ATUALIZADO: Recebe o nomeProduto
    public PedidoItemEntity(ProdutoEntity produto, String nomeProduto, int quantidade, BigDecimal precoUnitario, PedidoEntity pedido) {
        this.produto = produto;
        this.nomeProduto = nomeProduto; // NOVO
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.pedido = pedido;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public ProdutoEntity getProduto() { return produto; }
    public String getNomeProduto() { return nomeProduto; } // NOVO GETTER
    public int getQuantidade() { return quantidade; }
    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public PedidoEntity getPedido() { return pedido; }

    public void setId(Long id) { this.id = id; }
    public void setProduto(ProdutoEntity produto) { this.produto = produto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; } // NOVO SETTER
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }
    public void setPedido(PedidoEntity pedido) { this.pedido = pedido; }
}