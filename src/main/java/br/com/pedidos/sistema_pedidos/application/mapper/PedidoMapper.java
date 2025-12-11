package br.com.pedidos.sistema_pedidos.application.mapper;

import br.com.pedidos.sistema_pedidos.domain.model.*;
import br.com.pedidos.sistema_pedidos.infrastructure.persistence.entity.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PedidoMapper {

    public static PedidoEntity toEntity(Pedido domain) {

        ClienteEntity clienteEntity = new ClienteEntity();

        clienteEntity.setCpf(domain.getCliente().getCpf());

        PedidoEntity pedidoEntity = new PedidoEntity(
                domain.getCodigo(),
                clienteEntity,
                null,
                domain.getStatus(),
                domain.getValorTotal()
        );

        if (domain.getId() != null) {
            pedidoEntity.setId(domain.getId());
        }

        List<PedidoItemEntity> itensEntity = domain.getItens().stream()
                .map(item -> toEntity(item, pedidoEntity))
                .collect(Collectors.toList());

        pedidoEntity.setItens(itensEntity);

        return pedidoEntity;
    }

    public static PedidoItemEntity toEntity(PedidoItem itemDomain, PedidoEntity pedidoEntity) {

        ProdutoEntity produtoRef = new ProdutoEntity();
        produtoRef.setId(itemDomain.getProduto().getId());

        return new PedidoItemEntity(
                produtoRef,
                itemDomain.getNomeProduto(),
                itemDomain.getQuantidade(),
                itemDomain.getPrecoUnitario(),
                pedidoEntity
        );
    }

    public static Pedido toDomain(PedidoEntity entity) {


        Cliente clienteDomain = ClienteMapper.toDomain(entity.getCliente());

        List<PedidoItem> itensDomain = entity.getItens().stream()
                .map(PedidoMapper::toDomain)
                .collect(Collectors.toList());

        return new Pedido(
                entity.getId(),
                entity.getCodigo(),
                clienteDomain,
                itensDomain,
                entity.getStatus(),
                entity.getValorTotal()
        );
    }

    public static PedidoItem toDomain(PedidoItemEntity entity) {

        Produto produtoDomain = ProdutoMapper.toDomain(entity.getProduto());

        return new PedidoItem(
                entity.getId(),
                produtoDomain,
                entity.getQuantidade(),
                entity.getPrecoUnitario(),
                entity.getNomeProduto()
        );
    }
}