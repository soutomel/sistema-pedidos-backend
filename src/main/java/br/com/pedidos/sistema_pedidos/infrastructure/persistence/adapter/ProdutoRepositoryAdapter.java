package br.com.pedidos.sistema_pedidos.infrastructure.persistence.adapter;

import br.com.pedidos.sistema_pedidos.application.mapper.ProdutoMapper;
import br.com.pedidos.sistema_pedidos.domain.model.Produto;
import br.com.pedidos.sistema_pedidos.domain.repository.ProdutoRepositoryPort;
import br.com.pedidos.sistema_pedidos.infrastructure.persistence.entity.ProdutoEntity;
import br.com.pedidos.sistema_pedidos.infrastructure.persistence.repository.ProdutoJPARepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProdutoRepositoryAdapter implements ProdutoRepositoryPort {

    private final ProdutoJPARepository jpaRepository;

    public ProdutoRepositoryAdapter(ProdutoJPARepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    @CacheEvict(value = "produtos", allEntries = true)
    public Produto salvar(Produto produto) {
        ProdutoEntity entity = ProdutoMapper.toEntity(produto);
        ProdutoEntity savedEntity = jpaRepository.save(entity);
        return ProdutoMapper.toDomain(savedEntity);
    }

    @Override
    @Cacheable(value = "produtos", key = "#id")
    public Optional<Produto> buscarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(ProdutoMapper::toDomain);
    }

    @Override
    @Cacheable(value = "produtos")
    public List<Produto> listarTodos() {
        return jpaRepository.findAll()
                .stream()
                .map(ProdutoMapper::toDomain)
                .toList();
    }
}
