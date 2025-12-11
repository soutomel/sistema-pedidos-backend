package br.com.pedidos.sistema_pedidos.infrastructure.persistence.repository;

import br.com.pedidos.sistema_pedidos.infrastructure.persistence.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.Optional;
import br.com.pedidos.sistema_pedidos.domain.model.StatusPedido; // Novo import necessário
import java.util.List;

public interface PedidoJPARepository extends JpaRepository<PedidoEntity, Long> {
    // Busca por Código, útil para consultas públicas
    Optional<PedidoEntity> findByCodigo(UUID codigo);

    List<PedidoEntity> findByStatusIn(List<StatusPedido> status);

}