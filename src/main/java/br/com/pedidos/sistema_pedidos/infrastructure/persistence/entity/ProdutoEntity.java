package br.com.pedidos.sistema_pedidos.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private BigDecimal preco;

    // RELACIONAMENTO: Muitos Produtos para Uma Categoria

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaEntity categoria;

    // Construtores, Getters e Setters (Obrigatórios pelo JPA)
    public ProdutoEntity() {}

    // Construtor usado pelo Adapter
    public ProdutoEntity(String nome, BigDecimal preco, CategoriaEntity categoria) {
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
    }

    // Getters e Setters (necessários para o JPA)
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public BigDecimal getPreco() { return preco; }
    public CategoriaEntity getCategoria() { return categoria; }

    public void setId(Long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public void setCategoria(CategoriaEntity categoria) { this.categoria = categoria; }
}