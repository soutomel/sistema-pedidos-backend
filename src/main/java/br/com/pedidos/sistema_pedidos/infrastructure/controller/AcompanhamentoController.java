package br.com.pedidos.sistema_pedidos.infrastructure.controller;

import br.com.pedidos.sistema_pedidos.application.usecase.BuscarPedidosEmAndamentoUseCase;
import br.com.pedidos.sistema_pedidos.application.usecase.ConsultarTempoMedioUseCase; // <<< NOVO IMPORT
import br.com.pedidos.sistema_pedidos.domain.model.Pedido;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.Duration; // NOVO IMPORT
import java.util.List;
import java.util.stream.Collectors;

// Importa os DTOs de Resposta do PedidoController para reutilização
import static br.com.pedidos.sistema_pedidos.infrastructure.controller.PedidoController.PedidoResponse;

@RestController
@RequestMapping("/acompanhamento")
public class AcompanhamentoController {

    private final BuscarPedidosEmAndamentoUseCase buscarPedidosEmAndamentoUseCase;
    private final ConsultarTempoMedioUseCase consultarTempoMedioUseCase; // <<< DECLARAÇÃO

    // CONSTRUTOR ATUALIZADO para injetar ambos
    public AcompanhamentoController(
            BuscarPedidosEmAndamentoUseCase buscarPedidosEmAndamentoUseCase,
            ConsultarTempoMedioUseCase consultarTempoMedioUseCase
    ) {
        this.buscarPedidosEmAndamentoUseCase = buscarPedidosEmAndamentoUseCase;
        this.consultarTempoMedioUseCase = consultarTempoMedioUseCase;
    }

    @GetMapping("/pedidos/em-andamento")
    public ResponseEntity<List<PedidoResponse>> listarPedidosEmAndamento() {
        List<Pedido> pedidos = buscarPedidosEmAndamentoUseCase.executar();
        List<PedidoResponse> responses = pedidos.stream()
                .map(PedidoResponse::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // <<< NOVO ENDPOINT: MÉTRICAS DE TEMPO >>>
    @GetMapping("/metricas/tempo-medio")
    public ResponseEntity<TempoMedioResponse> consultarTempoMedio() {

        ConsultarTempoMedioUseCase.TempoMedio metricas = consultarTempoMedioUseCase.executar();

        // Mapeia para DTO de Resposta (Convertendo Duration para String legível)
        TempoMedioResponse response = new TempoMedioResponse(
                formatDuration(metricas.tempoPreparo()),
                formatDuration(metricas.tempoEspera())
        );

        return ResponseEntity.ok(response);
    }

    // Método auxiliar para formatação
    private String formatDuration(Duration duration) {
        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();
        return String.format("%d min %d seg", minutes, seconds);
    }

    // DTO de Resposta para a API de Métricas
    public record TempoMedioResponse(String tempoMedioPreparo, String tempoMedioEspera) {}
}