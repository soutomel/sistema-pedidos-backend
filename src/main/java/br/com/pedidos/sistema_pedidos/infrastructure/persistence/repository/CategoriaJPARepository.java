package br.com.pedidos.sistema_pedidos.infrastructure.persistence.repository;

import br.com.pedidos.sistema_pedidos.infrastructure.persistence.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaJPARepository extends JpaRepository<CategoriaEntity, Long> {
    // CRUD básico (já incluso)
}