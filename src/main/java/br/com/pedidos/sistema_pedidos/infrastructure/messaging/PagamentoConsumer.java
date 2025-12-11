package br.com.pedidos.sistema_pedidos.infrastructure.messaging;// Caminho: infrastructure/messaging/PagamentoConsumer.java

import br.com.pedidos.sistema_pedidos.domain.event.PedidoCriadoEvent;
import br.com.pedidos.sistema_pedidos.application.usecase.ProcessarPagamentoUseCase;
import br.com.pedidos.sistema_pedidos.domain.model.MetodoPagamento; // Novo Import
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class PagamentoConsumer {

    private final ProcessarPagamentoUseCase processarPagamentoUseCase;

    public PagamentoConsumer(ProcessarPagamentoUseCase processarPagamentoUseCase) {
        this.processarPagamentoUseCase = processarPagamentoUseCase;
    }

    private static final String QUEUE_NAME = "pagamentos.queue";

    @RabbitListener(queuesToDeclare = @Queue(QUEUE_NAME))
    public void processarPedidoCriado(PedidoCriadoEvent event) {

        System.out.println("<<< PAGAMENTO CONSUMER: Processando evento de Pedido Criado >>>");

        // Simulação de dados do Gateway de Pagamento (os dados reais viriam no corpo do evento)
        String statusGateway = "approved"; // Simula a aprovação do Mercado Pago
        MetodoPagamento metodo = MetodoPagamento.PIX;
        String transacaoId = UUID.randomUUID().toString();

        // Chamada do UseCase de Processamento de Pagamento
        processarPagamentoUseCase.executar(
                event.codigoPedido().toString(),
                statusGateway,
                metodo,
                transacaoId
        );

        System.out.printf("Pagamento Aprovado para Pedido %s. Status atualizado para RECEBIDO.\n", event.codigoPedido());
    }
}