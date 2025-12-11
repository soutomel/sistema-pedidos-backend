package br.com.pedidos.sistema_pedidos.application.usecase;

import br.com.pedidos.sistema_pedidos.domain.model.Produto;
import br.com.pedidos.sistema_pedidos.domain.model.Categoria;
import br.com.pedidos.sistema_pedidos.domain.repository.ProdutoRepositoryPort;
import br.com.pedidos.sistema_pedidos.domain.repository.CategoriaRepositoryPort;

import java.util.List;
import java.util.Locale;

class CategoriaNaoEncontradaException extends RuntimeException {
    public CategoriaNaoEncontradaException(Long categoriaId) {
        super("Categoria com ID " + categoriaId + " não encontrada.");
    }
    // <<< NOVO CONSTRUTOR DE EXCEÇÃO para o filtro de nome >>>
    public CategoriaNaoEncontradaException(String nomeCategoria) {
        super("Categoria '" + nomeCategoria + "' não é permitida para criação de produtos.");
    }
}

public class CriarProdutoUseCase {

    // <<< REQUISITO 6.b: Lista de categorias permitidas >>>
    private static final List<String> CATEGORIAS_PERMITIDAS = List.of(
            "LANCHE", "ACOMPANHAMENTO", "BEBIDA", "SOBREMESA"
    );

    private final ProdutoRepositoryPort produtoRepositoryPort;
    private final CategoriaRepositoryPort categoriaRepositoryPort;

    public CriarProdutoUseCase(ProdutoRepositoryPort produtoRepositoryPort, CategoriaRepositoryPort categoriaRepositoryPort) {
        this.produtoRepositoryPort = produtoRepositoryPort;
        this.categoriaRepositoryPort = categoriaRepositoryPort;
    }

    public Produto executar(Produto novoProduto, Long categoriaId) {

        Categoria categoriaExistente = categoriaRepositoryPort.buscarPorId(categoriaId)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(categoriaId));

        // 1. REGRA DE NEGÓCIO: Validar se a categoria é uma das fixas do sistema.
        String nomeCategoria = categoriaExistente.getNome().toUpperCase(Locale.ROOT);

        if (!CATEGORIAS_PERMITIDAS.contains(nomeCategoria)) {
            // Lança a exceção se a categoria não estiver na lista fixa
            throw new CategoriaNaoEncontradaException(categoriaExistente.getNome());
        }

        // 2. Continua com a criação
        Produto produtoFinal = new Produto(novoProduto.getNome(), novoProduto.getPreco(), categoriaExistente);

        return produtoRepositoryPort.salvar(produtoFinal);
    }
}