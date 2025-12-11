package br.com.pedidos.sistema_pedidos.domain.repository;

import br.com.pedidos.sistema_pedidos.domain.model.Produto;
import java.util.List;
import java.util.Optional;

public interface ProdutoRepositoryPort {

    Produto salvar(Produto produto);
    Optional<Produto> buscarPorId(Long id);

    List<Produto> listarTodos();
}
