package br.com.pedidos.sistema_pedidos.domain.model;

public enum StatusPedido {
    // 1. O pedido é criado e espera a confirmação do pagamento
    AGUARDANDO_PAGAMENTO,

    // 2. O pagamento foi recebido com sucesso
    RECEBIDO,

    // 3. A cozinha começou a preparar
    EM_PREPARACAO,

    // 4. O pedido está pronto para ser retirado/entregue
    PRONTO,

    // 5. Pedido entregue ao cliente
    FINALIZADO,

    // 6. Pagamento falhou ou cliente cancelou antes do preparo
    CANCELADO
}
