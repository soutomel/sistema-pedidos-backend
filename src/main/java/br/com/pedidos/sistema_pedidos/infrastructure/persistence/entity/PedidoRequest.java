package br.com.pedidos.sistema_pedidos.infrastructure.persistence.entity;
import java.util.List;

public record PedidoRequest(
        String clienteCpf, // <<< CORREÇÃO: Usar String (CPF)
        List<ItemRequest> itens
) {
    public record ItemRequest(
            Long produtoId,
            int quantidade
    ) {}
}