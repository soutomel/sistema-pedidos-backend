package br.com.pedidos.sistema_pedidos.application.usecase;

import br.com.pedidos.sistema_pedidos.domain.model.Categoria;
import br.com.pedidos.sistema_pedidos.domain.repository.CategoriaRepositoryPort;

public class CriarCategoriaUseCase {

    private final CategoriaRepositoryPort categoriaRepositoryPort;

    public CriarCategoriaUseCase(CategoriaRepositoryPort categoriaRepositoryPort) {
        this.categoriaRepositoryPort = categoriaRepositoryPort;
    }

    public Categoria executar(Categoria novaCategoria) {
        return categoriaRepositoryPort.salvar(novaCategoria);
    }
}