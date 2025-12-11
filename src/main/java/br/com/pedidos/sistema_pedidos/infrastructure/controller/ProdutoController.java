package br.com.pedidos.sistema_pedidos.infrastructure.controller;

import br.com.pedidos.sistema_pedidos.application.usecase.ListarProdutosUseCase;
import br.com.pedidos.sistema_pedidos.infrastructure.dto.ProdutoResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ListarProdutosUseCase listarProdutosUseCase;

    public ProdutoController(ListarProdutosUseCase listarProdutosUseCase) {
        this.listarProdutosUseCase = listarProdutosUseCase;
    }

    @GetMapping
    public List<ProdutoResponse> listar() {
        return listarProdutosUseCase.executar()
                .stream()
                .map(ProdutoResponse::fromDomain)
                .toList();
    }
}
