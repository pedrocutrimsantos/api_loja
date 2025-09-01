package br.com.pauloneto.loja.estoque.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MovEstoqueDtoIn(

        @NotNull
        @Schema(description = "Identificador único do produto movimentado",
                example = "101")
        UUID produtoId,

        @NotNull
        @Schema(description = "Identificador do depósito onde a movimentação será registrada",
                example = "5")
        UUID depositoId,

        @NotNull
        @Schema(description = "Tipo da movimentação de estoque (ENTRADA, SAIDA, AJUSTE)",
                example = "ENTRADA")
        String tipo,

        @NotNull
        @Schema(description = "Quantidade movimentada",
                example = "50.0")
        Double quantidade,

        @Schema(description = "Custo unitário do produto (apenas em entradas)",
                example = "12.50")
        Double custo,

        @Schema(description = "Origem da movimentação (fornecedor, devolução, ajuste, etc.)",
                example = "Fornecedor XYZ")
        String origem,

        @Schema(description = "Documento de referência da movimentação",
                example = "NF-12345")
        String docRef
) {}
