package br.com.pedidos.sistema_pedidos.infrastructure.controller;

public record ClienteRequest(
        String cpf,
        String nome,
        String email
) {}