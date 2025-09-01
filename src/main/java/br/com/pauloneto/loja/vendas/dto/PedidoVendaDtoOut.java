package br.com.pauloneto.loja.vendas.dto;

import java.util.List;
import java.util.UUID;

public record PedidoVendaDtoOut(UUID id, String status, Double totalBruto, Double descontoTotal, Double totalLiquido,
                                List<ItemPedidoDtoOut> itens) {}
