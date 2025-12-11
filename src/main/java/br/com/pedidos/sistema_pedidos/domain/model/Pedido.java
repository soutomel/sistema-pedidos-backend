package br.com.pedidos.sistema_pedidos.domain.model;

import br.com.pedidos.sistema_pedidos.domain.event.DomainEvent; // NOVO IMPORT
import br.com.pedidos.sistema_pedidos.domain.event.PedidoCriadoEvent; // NOVO IMPORT
import java.util.ArrayList; // NOVO IMPORT
import java.util.Collections; // NOVO IMPORT
import java.util.List;
import java.math.BigDecimal;
import java.util.UUID;

public class Pedido {

    private Long id;
    private final UUID codigo;
    private final Cliente cliente;
    private final List<PedidoItem> itens;
    private StatusPedido status;
    private final BigDecimal valorTotal;

    // <<< CAMPO EXISTENTE: Lista para armazenar eventos não disparados >>>
    private final List<DomainEvent> events = new ArrayList<>();

    // Construtor 1: Criação (Gera Código e Valor Total)
    public Pedido(Cliente cliente, List<PedidoItem> itens) {
        // CORRIGIDO: Permite cliente ser null, mas itens são obrigatórios.
        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException("Itens são obrigatórios para um pedido.");
        }

        this.codigo = UUID.randomUUID();
        this.cliente = cliente; // Cliente pode ser null (Guest Checkout)
        this.itens = itens;
        this.status = StatusPedido.AGUARDANDO_PAGAMENTO;
        this.valorTotal = calcularTotal();

        // <<< REGISTRA O EVENTO APÓS A CRIAÇÃO >>>
        // Ajustamos o CPF para o evento: usa o CPF do cliente ou "GUEST"
        String cpfEvento = cliente != null ? cliente.getCpf() : "GUEST";
        this.events.add(new PedidoCriadoEvent(this.codigo, cpfEvento, this.valorTotal));
    }

    // Construtor 2: Carregamento do DB (Não registra evento, é apenas para reconstrução)
    public Pedido(Long id, UUID codigo, Cliente cliente, List<PedidoItem> itens, StatusPedido status, BigDecimal valorTotal) {
        this.id = id;
        this.codigo = codigo;
        this.cliente = cliente;
        this.itens = itens;
        this.status = status;
        this.valorTotal = valorTotal;
    }

    // Método de Negócio 1: Cálculo
    private BigDecimal calcularTotal() {
        return itens.stream()
                .map(PedidoItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Método de Negócio 2: Transição de Status Específica
    public void marcarComoRecebido() {
        if (this.status != StatusPedido.AGUARDANDO_PAGAMENTO) {
            throw new IllegalStateException("Pedido só pode ser marcado como RECEBIDO se estiver AGUARDANDO_PAGAMENTO.");
        }
        this.status = StatusPedido.RECEBIDO;
    }

    // Getters
    public Long getId() { return id; }
    public UUID getCodigo() { return codigo; }
    public Cliente getCliente() { return cliente; }
    public List<PedidoItem> getItens() { return itens; }
    public StatusPedido getStatus() { return status; }
    public BigDecimal getValorTotal() { return valorTotal; }

    // Setter de ID
    public void setId(Long id) { this.id = id; }

    // Setter de Status
    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    // <<< MÉTODOS DE EVENTOS >>>
    public List<DomainEvent> getEvents() {
        return Collections.unmodifiableList(events);
    }

    public void clearEvents() {
        this.events.clear();
    }
}