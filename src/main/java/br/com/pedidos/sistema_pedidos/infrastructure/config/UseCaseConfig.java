package br.com.pedidos.sistema_pedidos.infrastructure.config;

import br.com.pedidos.sistema_pedidos.application.usecase.*;
import br.com.pedidos.sistema_pedidos.domain.repository.*;
// Imports da Porta de Mensageria:
import br.com.pedidos.sistema_pedidos.application.port.EventPublisherPort;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    // --- BEANS DE CRIAÇÃO ---

    @Bean
    public CriarClienteUseCase criarClienteUseCase(ClienteRepositoryPort clienteRepositoryPort) {
        return new CriarClienteUseCase(clienteRepositoryPort);
    }

    @Bean
    public CriarCategoriaUseCase criarCategoriaUseCase(CategoriaRepositoryPort categoriaRepositoryPort) {
        return new CriarCategoriaUseCase(categoriaRepositoryPort);
    }

    @Bean
    public CriarProdutoUseCase criarProdutoUseCase(
            ProdutoRepositoryPort produtoRepositoryPort,
            CategoriaRepositoryPort categoriaRepositoryPort
    ) {
        return new CriarProdutoUseCase(produtoRepositoryPort, categoriaRepositoryPort);
    }

    // --- BEAN PARA CRIAÇÃO DE PEDIDO ---
    @Bean
    public CriarPedidoUseCase criarPedidoUseCase(
            PedidoRepositoryPort pedidoRepositoryPort,
            ClienteRepositoryPort clienteRepositoryPort,
            ProdutoRepositoryPort produtoRepositoryPort
    ) {
        return new CriarPedidoUseCase(
                pedidoRepositoryPort,
                clienteRepositoryPort,
                produtoRepositoryPort
        );
    }

    // --- BEAN PARA PROCESSAMENTO DE PAGAMENTO (NOVO) ---
    @Bean
    public ProcessarPagamentoUseCase processarPagamentoUseCase(
            PedidoRepositoryPort pedidoRepositoryPort
    ) {
        // Este UseCase só precisa da Porta de Pedidos para buscar e salvar
        return new ProcessarPagamentoUseCase(pedidoRepositoryPort);
    }

    // --- BEANS DE CONSULTA E ATUALIZAÇÃO ---

    @Bean
    public AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase(
            PedidoRepositoryPort pedidoRepositoryPort
    ) {
        return new AtualizarStatusPedidoUseCase(pedidoRepositoryPort);
    }

    @Bean
    public BuscarPedidosEmAndamentoUseCase buscarPedidosEmAndamentoUseCase(
            PedidoRepositoryPort pedidoRepositoryPort
    ) {
        return new BuscarPedidosEmAndamentoUseCase(pedidoRepositoryPort);
    }

    @Bean
    public ConsultarTempoMedioUseCase consultarTempoMedioUseCase() {
        return new ConsultarTempoMedioUseCase();
    }
}