package br.com.pedidos.sistema_pedidos.infrastructure.dto;

import br.com.pedidos.sistema_pedidos.domain.model.Produto;

import java.math.BigDecimal;

public record ProdutoResponse(
        Long id,
        String nome,
        BigDecimal preco,
        CategoriaDTO categoria
) {

    public static ProdutoResponse fromDomain(Produto produto) {
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getCategoria() != null
                        ? new CategoriaDTO(produto.getCategoria().getId(), produto.getCategoria().getNome())
                        : null
        );
    }
}
