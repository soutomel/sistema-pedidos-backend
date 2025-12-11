package br.com.pedidos.sistema_pedidos.infrastructure.persistence.adapter;

import br.com.pedidos.sistema_pedidos.domain.model.Pedido;
import br.com.pedidos.sistema_pedidos.domain.repository.PedidoRepositoryPort;
import br.com.pedidos.sistema_pedidos.application.mapper.PedidoMapper;
import br.com.pedidos.sistema_pedidos.infrastructure.persistence.entity.PedidoEntity;
import br.com.pedidos.sistema_pedidos.infrastructure.persistence.repository.PedidoJPARepository;
import br.com.pedidos.sistema_pedidos.domain.model.StatusPedido;

import br.com.pedidos.sistema_pedidos.application.port.EventPublisherPort;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.UUID; // <<< NOVO IMPORT NECESSÁRIO!

@Component
public class PedidoRepositoryAdapter implements PedidoRepositoryPort {

    private final PedidoJPARepository jpaRepository;
    private final EventPublisherPort eventPublisherPort;

    public PedidoRepositoryAdapter(
            PedidoJPARepository jpaRepository,
            EventPublisherPort eventPublisherPort
    ) {
        this.jpaRepository = jpaRepository;
        this.eventPublisherPort = eventPublisherPort;
    }

    @Override
    public Pedido salvar(Pedido pedido) {
        PedidoEntity entity = PedidoMapper.toEntity(pedido);
        PedidoEntity savedEntity = jpaRepository.save(entity);

        Pedido savedPedidoDomain = PedidoMapper.toDomain(savedEntity);

        savedPedidoDomain.getEvents().forEach(eventPublisherPort::publish);
        savedPedidoDomain.clearEvents();

        return savedPedidoDomain;
    }



    @Override
    public Optional<Pedido> buscarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(PedidoMapper::toDomain);
    }

    @Override
    public List<Pedido> buscarPorStatusEmAndamento() {
        List<StatusPedido> statusEmAndamento = List.of(
                StatusPedido.RECEBIDO,
                StatusPedido.EM_PREPARACAO,
                StatusPedido.PRONTO
        );

        List<PedidoEntity> entities = jpaRepository.findByStatusIn(statusEmAndamento);

        return entities.stream()
                .map(PedidoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Pedido> buscarPorCodigo(String codigo) {
        try {
            // 1. Converte a String de entrada (da camada de Domínio) para UUID
            UUID codigoUuid = UUID.fromString(codigo);

            // 2. Chama o método do JPA Repository (que espera UUID)
            return jpaRepository.findByCodigo(codigoUuid)

                    // 3. Mapeia a Entity para o Domain Model
                    .map(PedidoMapper::toDomain);

        } catch (IllegalArgumentException e) {
            // Se a string não for um UUID válido, retorna Optional vazio.
            // Isso evita um Internal Server Error no caso de um código inválido.
            return Optional.empty();
        }
    }
}