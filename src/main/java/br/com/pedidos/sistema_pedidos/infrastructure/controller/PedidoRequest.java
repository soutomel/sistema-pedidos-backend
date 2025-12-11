package br.com.pedidos.sistema_pedidos.infrastructure.controller;

import java.util.List;

public record PedidoRequest(
        String cpfCliente,
        List<br.com.pedidos.sistema_pedidos.infrastructure.controller.PedidoRequest.ItemPedidoRequest> itens,
        Long enderecoId
) {
    public record ItemPedidoRequest(
            Long produtoId,
            int quantidade
    ) {}
}