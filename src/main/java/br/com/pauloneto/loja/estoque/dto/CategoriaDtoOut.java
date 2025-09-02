// br.com.pauloneto.loja.estoque.dto.categoria.CategoriaDtoOut.java
package br.com.pauloneto.loja.estoque.dto;

import java.util.UUID;

public record CategoriaDtoOut(
        UUID id,
        String nome,
        Boolean ativa
) {}
