package br.com.pedidos.sistema_pedidos.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class CategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera ID automaticamente
    private Long id;
    private String nome;

    public CategoriaEntity() {}
    public CategoriaEntity(String nome) { this.nome = nome; }

    // Getters e Setters (Obrigatórios pelo JPA)
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setId(Long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
}