package br.com.pedidos.sistema_pedidos.domain.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Evento disparado quando um novo pedido é criado.
 */
public record PedidoCriadoEvent(
        UUID codigoPedido,
        String clienteCpf,
        BigDecimal valorTotal,
        Instant occurredOn
) implements DomainEvent {

    public PedidoCriadoEvent(UUID codigoPedido, String clienteCpf, BigDecimal valorTotal) {
        this(codigoPedido, clienteCpf, valorTotal, Instant.now());
    }

    @Override
    public Instant getOcurredOn() {
        return occurredOn;
    }
}