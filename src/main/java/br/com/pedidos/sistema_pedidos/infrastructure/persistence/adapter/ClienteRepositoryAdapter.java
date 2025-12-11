package br.com.pedidos.sistema_pedidos.infrastructure.persistence.adapter;

import br.com.pedidos.sistema_pedidos.domain.model.Cliente;
import br.com.pedidos.sistema_pedidos.domain.repository.ClienteRepositoryPort;
import br.com.pedidos.sistema_pedidos.application.mapper.ClienteMapper;
import br.com.pedidos.sistema_pedidos.infrastructure.persistence.entity.ClienteEntity;
import br.com.pedidos.sistema_pedidos.infrastructure.persistence.repository.ClienteJPARepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component // Anotação Spring
public class ClienteRepositoryAdapter implements ClienteRepositoryPort {

    private final ClienteJPARepository jpaRepository;

    public ClienteRepositoryAdapter(ClienteJPARepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Cliente salvar(Cliente cliente) {
        ClienteEntity entity = ClienteMapper.toEntity(cliente);
        ClienteEntity savedEntity = jpaRepository.save(entity);
        return ClienteMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Cliente> buscarPorCpf(String cpf) {
        return jpaRepository.findByCpf(cpf)
                .map(ClienteMapper::toDomain); // Usa o Mapper para converter Entity -> Domain
    }

    // <<< IMPLEMENTAÇÃO DO MÉTODO FALTANTE >>>
    // Ele é exigido pela Porta, mas como o Cliente do Domínio usa CPF (String) como chave principal,
    // o método de busca por Long ID não é suportado e lançamos uma exceção.
    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        // Lançamos a exceção, pois o fluxo de Pedidos foi ajustado para usar buscarPorCpf(String).
        // Se a busca por ID fosse necessária, este adapter usaria ClienteJPARepository.findById(id).
        throw new UnsupportedOperationException("Busca por ID Long não é suportada para esta entidade. Use buscarPorCpf.");
    }
}