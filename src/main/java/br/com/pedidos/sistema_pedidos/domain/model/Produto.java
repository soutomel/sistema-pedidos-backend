package br.com.pedidos.sistema_pedidos.domain.model;

import java.math.BigDecimal;
import java.io.Serializable;

public class Produto implements Serializable {

    private Long id;
    private final String nome;
    private final BigDecimal preco;
    private final Categoria categoria;

    // Construtor 1: Para CRIAR (sem ID) - Contém a validação de negócio
    public Produto(String nome, BigDecimal preco, Categoria categoria) {
        // Validação de Negócio (só deve ser executada na criação)
        if (nome == null || preco == null || categoria == null) {
            throw new IllegalArgumentException("Nome, preço e categoria são obrigatórios.");
        }
        if (preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O preço deve ser positivo.");
        }
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
    }

    // Construtor 2: Para RETORNAR do DB (com ID) - CORRIGIDO: Nenhuma validação
    public Produto(Long id, String nome, BigDecimal preco, Categoria categoria) {
        // CORRIGIDO: Atribuição direta. Assumimos que o DB garante o NOT NULL.
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
    }

    // Getters e Setter de ID
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public BigDecimal getPreco() { return preco; }
    public Categoria getCategoria() { return categoria; }

    public void setId(Long id) { this.id = id; }
}