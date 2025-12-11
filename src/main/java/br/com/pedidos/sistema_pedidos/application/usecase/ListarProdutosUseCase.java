package br.com.pedidos.sistema_pedidos.application.usecase;

import br.com.pedidos.sistema_pedidos.domain.model.Produto;
import br.com.pedidos.sistema_pedidos.domain.repository.ProdutoRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarProdutosUseCase {

    private final ProdutoRepositoryPort repository;

    public ListarProdutosUseCase(ProdutoRepositoryPort repository) {
        this.repository = repository;
    }

    public List<Produto> executar() {
        return repository.listarTodos();
    }
}
