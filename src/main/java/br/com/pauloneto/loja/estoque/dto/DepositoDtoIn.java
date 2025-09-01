
package br.com.pauloneto.loja.estoque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DepositoDtoIn(@NotBlank @Size(max = 120) String nome) {}
