package br.com.pauloneto.loja.estoque.dto;

import java.util.UUID;
import java.math.BigDecimal;

public record PosicaoEstoqueDtoOut(
        UUID id,
        UUID produtoId,
        UUID depositoId,
        BigDecimal qtdDisponivel,
        BigDecimal qtdReservada,
        BigDecimal minimo,
        BigDecimal maximo
) {}
