package br.com.pedidos.sistema_pedidos.infrastructure.controller;

import br.com.pedidos.sistema_pedidos.application.usecase.CriarPedidoUseCase;
import br.com.pedidos.sistema_pedidos.application.usecase.AtualizarStatusPedidoUseCase;
import br.com.pedidos.sistema_pedidos.domain.model.Pedido;
import br.com.pedidos.sistema_pedidos.domain.model.StatusPedido;
import br.com.pedidos.sistema_pedidos.infrastructure.controller.PedidoRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final CriarPedidoUseCase criarPedidoUseCase;
    private final AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase;

    public PedidoController(
            CriarPedidoUseCase criarPedidoUseCase,
            AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase
    ) {
        this.criarPedidoUseCase = criarPedidoUseCase;
        this.atualizarStatusPedidoUseCase = atualizarStatusPedidoUseCase;
    }

    // ---------------------- CRIAR PEDIDO -------------------------
    @PostMapping
    public ResponseEntity<PedidoResponse> criar(@RequestBody br.com.pedidos.sistema_pedidos.infrastructure.controller.PedidoRequest request) {

        // 🔥 CONVERSÃO DO DTO → COMMAND
        CriarPedidoUseCase.CriarPedidoCommand command =
                new CriarPedidoUseCase.CriarPedidoCommand(
                        request.cpfCliente(),
                        request.itens().stream()
                                .map(i -> new CriarPedidoUseCase.ItemCommand(i.produtoId(), i.quantidade()))
                                .toList()
                );

        Pedido pedido = criarPedidoUseCase.executar(command);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PedidoResponse.fromDomain(pedido));
    }


    // ---------------------- ATUALIZAR STATUS -------------------------
    @PutMapping("/{id}/status")
    public ResponseEntity<PedidoResponse> atualizarStatus(
            @PathVariable Long id,
            @RequestBody StatusUpdateRequest dto
    ) {
        StatusPedido novo = StatusPedido.valueOf(dto.novoStatus());
        Pedido pedido = atualizarStatusPedidoUseCase.executar(id, novo);
        return ResponseEntity.ok(PedidoResponse.fromDomain(pedido));
    }

    // DTOs
    public record StatusUpdateRequest(String novoStatus) {}

    public record PedidoResponse(
            Long id,
            UUID codigo,
            String cpfCliente,
            java.math.BigDecimal valorTotal,
            StatusPedido status,
            List<ItemResponse> itens
    ) {
        public static PedidoResponse fromDomain(Pedido p) {
            List<ItemResponse> itens = p.getItens().stream()
                    .map(i -> new ItemResponse(
                            i.getProduto().getId(),
                            i.getNomeProduto(),
                            i.getQuantidade(),
                            i.getSubtotal()
                    )).toList();

            return new PedidoResponse(
                    p.getId(),
                    p.getCodigo(),
                    p.getCliente() != null ? p.getCliente().getCpf() : null,
                    p.getValorTotal(),
                    p.getStatus(),
                    itens
            );
        }
    }

    public record ItemResponse(
            Long produtoId,
            String nomeProduto,
            int quantidade,
            java.math.BigDecimal subtotal
    ) {}
}
