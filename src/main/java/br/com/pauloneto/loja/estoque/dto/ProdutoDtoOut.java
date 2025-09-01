package br.com.pauloneto.loja.estoque.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record ProdutoDtoOut(

        @Schema(description = "Identificador único do produto",
                example = "101")
        UUID id,

        @Schema(description = "Código interno do produto (SKU - Stock Keeping Unit)",
                example = "SKU-001")
        String sku,

        @Schema(description = "Código de barras EAN do produto",
                example = "7891234567890")
        String ean,

        @Schema(description = "Descrição detalhada do produto",
                example = "Camiseta Polo Azul Tamanho M")
        String descricao,

        @Schema(description = "Nomenclatura Comum do Mercosul (NCM) usada para tributação",
                example = "61091000")
        String ncm,

        @Schema(description = "Unidade de medida do produto",
                example = "UN")
        String unidade,

        @Schema(description = "Peso do produto em quilogramas",
                example = "0.35")
        Double peso,

        @Schema(description = "Volume do produto em metros cúbicos",
                example = "0.0025")
        Double volume,

        @Schema(description = "Identificador da categoria do produto",
                example = "10")
        UUID categoriaId,

        @Schema(description = "Marca do produto",
                example = "Nike")
        String marca,

        @Schema(description = "Indica se o produto está ativo para vendas",
                example = "true")
        Boolean ativo
) {}
