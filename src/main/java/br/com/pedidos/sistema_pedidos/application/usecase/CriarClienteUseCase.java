package br.com.pedidos.sistema_pedidos.application.usecase;

import br.com.pedidos.sistema_pedidos.domain.model.Cliente;
import br.com.pedidos.sistema_pedidos.domain.repository.ClienteRepositoryPort;
import java.util.Optional;

public class CriarClienteUseCase {

    private final ClienteRepositoryPort clienteRepositoryPort;

    public CriarClienteUseCase(ClienteRepositoryPort clienteRepositoryPort) {
        this.clienteRepositoryPort = clienteRepositoryPort;
    }

    public Cliente executar(Cliente novoCliente) {

        Optional<Cliente> clienteExistente = clienteRepositoryPort.buscarPorCpf(novoCliente.getCpf());

        if (clienteExistente.isPresent()) {
            return clienteExistente.get();
        }

        return clienteRepositoryPort.salvar(novoCliente);
    }
}