package br.com.pedidos.sistema_pedidos.domain.model;

import java.io.Serializable;

public class Categoria implements Serializable {

    // O ID deve ser um Long para ser gerado automaticamente pelo DB (JPA)
    private Long id;
    private final String nome;

    // Construtor 1: Usado pelo repositório (após o DB gerar o ID)
    public Categoria(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Construtor 2: Usado pelo Caso de Uso (antes de salvar)
    public Categoria(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da categoria é obrigatório.");
        }
        this.nome = nome;
    }

    // Construtor 3: NOVO - Usado pelo ProdutoController para criar uma categoria dummy com ID.
    // O ProdutoController usará este construtor para fins de busca/relacionamento.
    public Categoria(Long id) {
        this.id = id;
        this.nome = null; // O nome é nulo/vazio, pois só precisamos do ID neste contexto
    }

    // Getters e Setters (O Setter é necessário para o Adapter injetar o ID após salvar)
    public Long getId() { return id; }
    public String getNome() { return nome; }

    // Setter de ID: Necessário para que o Adapter possa injetar o ID gerado pelo DB.
    public void setId(Long id) { this.id = id; }
}