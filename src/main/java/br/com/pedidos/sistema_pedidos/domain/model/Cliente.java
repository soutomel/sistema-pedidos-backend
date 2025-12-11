package br.com.pedidos.sistema_pedidos.domain.model;

public class Cliente {

    private final String cpf;
    private final String nome;
    private final String email;

    // Construtor
    public Cliente(String cpf, String nome, String email) {
        if (cpf == null || cpf.length() != 11) {
            throw new IllegalArgumentException("CPF deve ter 11 dígitos.");
        }

        // <<< VALIDAÇÕES REFORÇADAS CONFORME REQUISITO >>>
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório para o cadastro.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("E-mail é obrigatório para o cadastro.");
        }

        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
    }

    // Getters
    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
}