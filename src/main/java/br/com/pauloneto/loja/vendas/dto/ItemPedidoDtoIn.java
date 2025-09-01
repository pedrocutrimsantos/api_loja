package br.com.pauloneto.loja.vendas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ItemPedidoDtoIn(

        @Schema(description = "Identificador do produto (UUID da tabela produto)",
                example = "550e8400-e29b-41d4-a716-446655440000")
        @NotNull
        UUID produtoId,

        @Schema(description = "Quantidade solicitada do produto",
                example = "2.0")
        @NotNull
        Double quantidade,

        @Schema(description = "Preço unitário aplicado ao item do pedido",
                example = "49.90")
        @NotNull
        Double precoUnitario,

        @Schema(description = "Desconto aplicado ao item (valor monetário ou percentual, dependendo da regra)",
                example = "5.0")
        Double desconto
) {}
