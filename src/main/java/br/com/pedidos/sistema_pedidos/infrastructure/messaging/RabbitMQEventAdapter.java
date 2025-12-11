package br.com.pedidos.sistema_pedidos.infrastructure.messaging;

import br.com.pedidos.sistema_pedidos.application.port.EventPublisherPort;
import br.com.pedidos.sistema_pedidos.domain.event.DomainEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate; // <<< NOVO IMPORT CRÍTICO
import org.springframework.stereotype.Component;

/**
 * Adapter de Saída para Mensageria.
 * Implementa a EventPublisherPort usando o RabbitTemplate do Spring AMQP.
 */
@Component
public class RabbitMQEventAdapter implements EventPublisherPort {

    // Agora injetamos o RabbitTemplate real
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQEventAdapter(RabbitTemplate rabbitTemplate) { // <<< INJEÇÃO REAL
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(DomainEvent event) {

        // 1. Lógica real: Serializa e envia o evento para a fila
        String routingKey = event.getClass().getSimpleName();

        // Usa o RabbitTemplate para enviar a mensagem para uma fila que tenha o mesmo nome do evento
        // O PagamentoConsumer deve escutar esta fila (que é o próximo passo)
        rabbitTemplate.convertAndSend(routingKey, event);

        // 2. Log de confirmação
        System.out.println("-------------------------------------------------------------------");
        System.out.printf(">>> RABBITMQ ADAPTER: Evento %s ENVIADO COM SUCESSO.\n", routingKey);
        System.out.println(">>> Fila: " + routingKey + ". Pagamento processado de forma assíncrona.");
        System.out.println("-------------------------------------------------------------------");
    }
}