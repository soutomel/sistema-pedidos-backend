package br.com.pedidos.sistema_pedidos.infrastructure.event;

import br.com.pedidos.sistema_pedidos.domain.event.PedidoCriadoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoEventListener {

    // Este método é chamado automaticamente pelo Spring quando um PedidoCriadoEvent é disparado
    @EventListener
    public void handlePedidoCriado(PedidoCriadoEvent event) {

        System.out.println("-------------------------------------------------------------------");
        System.out.println("<<< SPRINT 5: EVENTO RECEBIDO >>>");
        System.out.printf("Pedido %s (CPF: %s, Valor: %.2f) criado com sucesso.\n",
                event.codigoPedido(), event.clienteCpf(), event.valorTotal());
        System.out.println(">>> Enviando para a fila de Pagamento (RabbitMQ/Kafka)...");
        System.out.println("-------------------------------------------------------------------");

        // Futuramente: A lógica de enviar para o RabbitMQ ou Kafka seria implementada aqui.
    }
}
