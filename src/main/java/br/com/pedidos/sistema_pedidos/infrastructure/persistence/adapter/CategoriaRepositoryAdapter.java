package br.com.pedidos.sistema_pedidos.infrastructure.persistence.adapter;

import br.com.pedidos.sistema_pedidos.domain.model.Categoria;
import br.com.pedidos.sistema_pedidos.domain.repository.CategoriaRepositoryPort;
import br.com.pedidos.sistema_pedidos.application.mapper.CategoriaMapper;
import br.com.pedidos.sistema_pedidos.infrastructure.persistence.repository.CategoriaJPARepository;
import br.com.pedidos.sistema_pedidos.infrastructure.persistence.entity.CategoriaEntity; // <<< LINHA CORRIGIDA
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoriaRepositoryAdapter implements CategoriaRepositoryPort {

    private final CategoriaJPARepository jpaRepository;

    public CategoriaRepositoryAdapter(CategoriaJPARepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Categoria salvar(Categoria categoria) {
        // Agora o compilador sabe o que é CategoriaEntity
        CategoriaEntity entity = CategoriaMapper.toEntity(categoria);
        CategoriaEntity savedEntity = jpaRepository.save(entity);
        return CategoriaMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Categoria> buscarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(CategoriaMapper::toDomain);
    }
}