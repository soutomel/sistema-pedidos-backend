package br.com.pedidos.sistema_pedidos.infrastructure.controller;

import java.math.BigDecimal;

// DTO de entrada para o Controller
public record ProdutoRequest(
        String nome,
        BigDecimal preco,
        Long categoriaId // O ID da Categoria que o cliente deseja associar
) {}