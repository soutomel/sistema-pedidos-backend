package br.com.pedidos.sistema_pedidos.domain.repository;

import br.com.pedidos.sistema_pedidos.domain.model.Categoria;
import java.util.Optional;

public interface CategoriaRepositoryPort {

    Categoria salvar(Categoria categoria);
    Optional<Categoria> buscarPorId(Long id); // Novo método para Produto usar
}