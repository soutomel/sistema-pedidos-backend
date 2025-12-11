package br.com.pedidos.sistema_pedidos.infrastructure.controller;

import br.com.pedidos.sistema_pedidos.application.usecase.ProcessarPagamentoUseCase;
import br.com.pedidos.sistema_pedidos.domain.model.MetodoPagamento; // <<< IMPORT NECESSÁRIO
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID; // <<< IMPORT NECESSÁRIO

@RestController
@RequestMapping("/webhooks/mercado-pago")
public class WebhookController {

    private final ProcessarPagamentoUseCase processarPagamentoUseCase;

    public WebhookController(ProcessarPagamentoUseCase processarPagamentoUseCase) {
        this.processarPagamentoUseCase = processarPagamentoUseCase;
    }

    /**
     * Simula o endpoint que o Mercado Pago chamaria para notificar um evento.
     * URL de teste: POST /webhooks/mercado-pago/notificacao
     */
    @PostMapping("/notificacao")
    public ResponseEntity<Void> receberNotificacaoPagamento(@RequestBody WebhookRequest request) {

        System.out.println("<<< WEBHOOK RECEBIDO: Notificação de pagamento. >>>");

        // 1. Simular Método de Pagamento (PIX como padrão no webhook)
        MetodoPagamento metodo = MetodoPagamento.PIX;

        // 2. Simular ID da Transação (Geramos um novo UUID, já que o Webhook não nos deu um ID de transação)
        String transacaoId = UUID.randomUUID().toString();

        // CORRIGIDO: Passa os QUATRO argumentos que o UseCase espera
        boolean sucesso = processarPagamentoUseCase.executar(
                request.codigoPedido(),
                request.status(),
                metodo,         // Terceiro argumento
                transacaoId     // Quarto argumento
        );

        if (sucesso) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // DTO que simula o mínimo que o webhook precisa
    public record WebhookRequest(String codigoPedido, String status) {}
}