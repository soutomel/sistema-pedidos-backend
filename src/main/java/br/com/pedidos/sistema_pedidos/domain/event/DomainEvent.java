package br.com.pedidos.sistema_pedidos.domain.event;

import java.time.Instant;

/**
 * Interface base para todos os eventos de domínio.
 */
public interface DomainEvent {
    Instant getOcurredOn();
}