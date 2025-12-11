package br.com.pedidos.sistema_pedidos.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class ClienteEntity {

    @Id
    private String cpf;
    private String nome;
    private String email;

    // Construtor vazio (obrigatório pelo JPA)
    public ClienteEntity() {}

    // Construtor completo
    public ClienteEntity(String cpf, String nome, String email) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
    }

    // Getters e Setters (Obrigatórios pelo JPA)
    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }
}