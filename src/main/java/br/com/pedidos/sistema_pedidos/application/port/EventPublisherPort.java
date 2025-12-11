package br.com.pedidos.sistema_pedidos.application.port;

import br.com.pedidos.sistema_pedidos.domain.event.DomainEvent;

/**
 * Porta de Saída para publicar eventos de domínio de forma assíncrona.
 * A implementação será o Adapter de Mensageria (ex: RabbitMQEventAdapter).
 */
public interface EventPublisherPort {
    void publish(DomainEvent event);
}