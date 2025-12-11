package br.com.pedidos.sistema_pedidos.infrastructure.controller;

// DTO para representar um item individual no pedido (usado em itensSimples e Combo)
public record ItemRequest(
        Long produtoId,
        int quantidade
) {}
