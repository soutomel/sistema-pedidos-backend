package br.com.pedidos.sistema_pedidos.infrastructure.persistence.repository;

import br.com.pedidos.sistema_pedidos.infrastructure.persistence.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import br.com.pedidos.sistema_pedidos.infrastructure.persistence.repository.ClienteJPARepository;

public interface ClienteJPARepository extends JpaRepository<ClienteEntity, String> {
    Optional<ClienteEntity> findByCpf(String cpf);
}