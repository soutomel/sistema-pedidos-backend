package br.com.pedidos.sistema_pedidos.application.mapper;

import br.com.pedidos.sistema_pedidos.domain.model.Categoria;
import br.com.pedidos.sistema_pedidos.infrastructure.controller.CategoriaRequest;
import br.com.pedidos.sistema_pedidos.infrastructure.persistence.entity.CategoriaEntity;

public class CategoriaMapper {
    public static Categoria toDomain(CategoriaRequest request) {
        return new Categoria(request.nome());
    }

    public static CategoriaEntity toEntity(Categoria domain) {
        return new CategoriaEntity(domain.getNome());
    }

    public static Categoria toDomain(CategoriaEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Categoria(entity.getId(), entity.getNome());
    }
}