package br.com.pauloneto.loja.vendas.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record ItemPedidoDtoOut(

        @Schema(description = "Identificador único do item do pedido",
                example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,

        @Schema(description = "Identificador do produto relacionado (UUID da tabela produto ou Long, conforme sua modelagem)",
                example = "550e8400-e29b-41d4-a716-446655440000")
        UUID produtoId,

        @Schema(description = "Quantidade do produto no item",
                example = "2.0")
        Double quantidade,

        @Schema(description = "Preço unitário do produto",
                example = "49.90")
        Double precoUnitario,

        @Schema(description = "Desconto aplicado ao item",
                example = "5.0")
        Double desconto,

        @Schema(description = "Valor total calculado do item (quantidade * preço - desconto)",
                example = "94.80")
        Double totalItem
) {}
