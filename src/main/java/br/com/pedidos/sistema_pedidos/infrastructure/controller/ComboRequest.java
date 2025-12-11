package br.com.pedidos.sistema_pedidos.infrastructure.controller;

// <<< NOVO DTO: Estrutura de Combo de Lanches >>>
// Representa as escolhas específicas do combo (Requisito 1.c)
public record ComboRequest(
        ItemRequest lanche,
        ItemRequest acompanhamento,
        ItemRequest bebida,
        ItemRequest sobremesa
) {}
