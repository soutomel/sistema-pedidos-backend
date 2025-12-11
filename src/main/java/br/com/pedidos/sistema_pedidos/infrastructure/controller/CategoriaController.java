package br.com.pedidos.sistema_pedidos.infrastructure.controller;

import br.com.pedidos.sistema_pedidos.application.usecase.CriarCategoriaUseCase;
import br.com.pedidos.sistema_pedidos.domain.model.Categoria;
import br.com.pedidos.sistema_pedidos.application.mapper.CategoriaMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// Importando o DTO que acabamos de criar
import br.com.pedidos.sistema_pedidos.infrastructure.controller.CategoriaRequest;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CriarCategoriaUseCase criarCategoriaUseCase;

    public CategoriaController(CriarCategoriaUseCase criarCategoriaUseCase) {
        this.criarCategoriaUseCase = criarCategoriaUseCase;
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> criarCategoria(@RequestBody CategoriaRequest request) {

        Categoria novaCategoria = CategoriaMapper.toDomain(request);
        Categoria categoriaSalva = criarCategoriaUseCase.executar(novaCategoria);

        // Usamos a classe de resposta definida internamente
        CategoriaResponse response = new CategoriaResponse(categoriaSalva.getId(), categoriaSalva.getNome());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Mantemos apenas a classe de Resposta aqui
    public record CategoriaResponse(Long id, String nome) {}
}