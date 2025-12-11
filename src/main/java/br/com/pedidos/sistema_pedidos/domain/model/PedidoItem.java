package br.com.pedidos.sistema_pedidos.domain.model;

import java.math.BigDecimal;

public class PedidoItem {

    private Long id; // ID gerado pelo DB
    private final Produto produto; // Referência ao produto do catálogo
    private final String nomeProduto; // NOVO: Nome do produto fixado no pedido
    private final int quantidade;
    private final BigDecimal precoUnitario; // Preço fixado no momento da compra

    // Construtor principal (para CRIAR)
    public PedidoItem(Produto produto, int quantidade, BigDecimal precoUnitario, String nomeProduto) {
        if (quantidade <= 0 || precoUnitario == null || precoUnitario.compareTo(BigDecimal.ZERO) <= 0 || nomeProduto == null || nomeProduto.isBlank()) {
            throw new IllegalArgumentException("Quantidade, preço unitário e nome do produto devem ser válidos.");
        }
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.nomeProduto = nomeProduto; // Atribuição do nome
    }

    // Construtor para retorno do DB (com ID)
    public PedidoItem(Long id, Produto produto, int quantidade, BigDecimal precoUnitario, String nomeProduto) {
        this(produto, quantidade, precoUnitario, nomeProduto);
        this.id = id;
    }

    // Getters
    public Long getId() { return id; }
    public Produto getProduto() { return produto; }
    public String getNomeProduto() { return nomeProduto; } // NOVO GETTER
    public int getQuantidade() { return quantidade; }
    public BigDecimal getPrecoUnitario() { return precoUnitario; }

    // Setter de ID
    public void setId(Long id) { this.id = id; }

    // Método de Negócio: Calcula o subtotal do item
    public BigDecimal getSubtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}