package br.com.pedidos.sistema_pedidos.application.mapper;

import br.com.pedidos.sistema_pedidos.domain.model.Cliente;
import br.com.pedidos.sistema_pedidos.infrastructure.controller.ClienteRequest;
import br.com.pedidos.sistema_pedidos.infrastructure.persistence.entity.ClienteEntity;

public class ClienteMapper {

    public static Cliente toDomain(ClienteRequest request) {
        return new Cliente(request.cpf(), request.nome(), request.email());
    }

    public static ClienteEntity toEntity(Cliente domain) {
        return new ClienteEntity(domain.getCpf(), domain.getNome(), domain.getEmail());
    }

    public static Cliente toDomain(ClienteEntity entity) {
        return new Cliente(entity.getCpf(), entity.getNome(), entity.getEmail());
    }
}
