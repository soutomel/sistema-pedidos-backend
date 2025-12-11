package br.com.pedidos.sistema_pedidos.infrastructure.persistence.repository;

import br.com.pedidos.sistema_pedidos.infrastructure.persistence.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoJPARepository extends JpaRepository<ProdutoEntity, Long> {
    // Métodos CRUD básicos herdados.
}