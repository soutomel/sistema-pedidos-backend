package br.com.pedidos.sistema_pedidos.domain.repository;

import br.com.pedidos.sistema_pedidos.domain.model.Cliente;
import java.util.Optional;

public interface ClienteRepositoryPort {

    Cliente salvar(Cliente cliente);
    // Mantenha buscarPorCpf (originalmente criado)
    Optional<Cliente> buscarPorCpf(String cpf);

    // <<< NOVO MÉTODO NECESSÁRIO PARA O PEDIDO USECASE >>>
    Optional<Cliente> buscarPorId(Long id);
}