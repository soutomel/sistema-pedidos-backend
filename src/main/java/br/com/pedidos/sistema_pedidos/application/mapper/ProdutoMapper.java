package br.com.pedidos.sistema_pedidos.application.mapper;

import br.com.pedidos.sistema_pedidos.domain.model.Produto;
import br.com.pedidos.sistema_pedidos.domain.model.Categoria;
import br.com.pedidos.sistema_pedidos.infrastructure.controller.ProdutoRequest;
import br.com.pedidos.sistema_pedidos.infrastructure.persistence.entity.ProdutoEntity;
import br.com.pedidos.sistema_pedidos.infrastructure.persistence.entity.CategoriaEntity; // <<< IMPORT CORRIGIDO
import java.math.BigDecimal;

public class ProdutoMapper {
    public static Produto toDomain(ProdutoRequest request, Categoria categoria) {
        return new Produto(request.nome(), request.preco(), categoria);
    }

    public static ProdutoEntity toEntity(Produto domain) {
        CategoriaEntity categoriaEntity = CategoriaMapper.toEntity(domain.getCategoria());
        categoriaEntity.setId(domain.getCategoria().getId());

        return new ProdutoEntity(domain.getNome(), domain.getPreco(), categoriaEntity);
    }

    public static Produto toDomain(ProdutoEntity entity) {
        Categoria categoriaDomain = CategoriaMapper.toDomain(entity.getCategoria());

        return new Produto(entity.getId(), entity.getNome(), entity.getPreco(), categoriaDomain);
    }
}