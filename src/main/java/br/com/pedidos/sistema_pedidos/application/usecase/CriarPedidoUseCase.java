package br.com.pedidos.sistema_pedidos.application.usecase;

import br.com.pedidos.sistema_pedidos.domain.model.*;
import br.com.pedidos.sistema_pedidos.domain.repository.ClienteRepositoryPort;
import br.com.pedidos.sistema_pedidos.domain.repository.PedidoRepositoryPort;
import br.com.pedidos.sistema_pedidos.domain.repository.ProdutoRepositoryPort;

import java.util.ArrayList;
import java.util.List;

public class CriarPedidoUseCase {

    private final PedidoRepositoryPort pedidoRepo;
    private final ClienteRepositoryPort clienteRepo;
    private final ProdutoRepositoryPort produtoRepo;

    public CriarPedidoUseCase(
            PedidoRepositoryPort pedidoRepo,
            ClienteRepositoryPort clienteRepo,
            ProdutoRepositoryPort produtoRepo
    ) {
        this.pedidoRepo = pedidoRepo;
        this.clienteRepo = clienteRepo;
        this.produtoRepo = produtoRepo;
    }

    /**
     * Comando interno para criação do pedido — evita dependência do controller
     */
    public record ItemCommand(Long produtoId, int quantidade) {}
    public record CriarPedidoCommand(String cpfCliente, List<ItemCommand> itens) {}

    public Pedido executar(CriarPedidoCommand command) {

        if (command.itens() == null || command.itens().isEmpty()) {
            throw new IllegalArgumentException("Pedido deve possuir itens.");
        }

        // --- BUSCA DO CLIENTE ---
        Cliente cliente = null;
        if (command.cpfCliente() != null && !command.cpfCliente().isBlank()) {
            cliente = clienteRepo.buscarPorCpf(command.cpfCliente())
                    .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));
        }

        // --- CRIA A LISTA DE ITENS DO PEDIDO ---
        List<PedidoItem> itensCriados = new ArrayList<>();

        for (ItemCommand item : command.itens()) {

            Produto produto = produtoRepo.buscarPorId(item.produtoId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + item.produtoId()));

            PedidoItem pedidoItem = new PedidoItem(
                    produto,
                    item.quantidade(),
                    produto.getPreco(),
                    produto.getNome()
            );

            itensCriados.add(pedidoItem);
        }

        // --- CRIA O PEDIDO ---
        Pedido pedido = new Pedido(cliente, itensCriados);

        // --- PERSISTE ---
        return pedidoRepo.salvar(pedido);
    }
}
