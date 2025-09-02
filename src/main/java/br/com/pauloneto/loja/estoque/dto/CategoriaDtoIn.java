// br.com.pauloneto.loja.estoque.dto.categoria.CategoriaDtoIn.java
package br.com.pauloneto.loja.estoque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaDtoIn(
        @NotBlank @Size(max = 120) String nome,
        Boolean ativa
) {}
