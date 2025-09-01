package br.com.pauloneto.loja.vendas.controller;

import br.com.pauloneto.loja.vendas.dto.ItemPedidoDtoIn;
import br.com.pauloneto.loja.vendas.dto.PedidoVendaDtoOut;
import br.com.pauloneto.loja.vendas.service.VendasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/vendas")
@Tag(name = "Vendas", description = "Fluxo de pedidos de venda")
@SecurityRequirement(name = "bearerAuth") // 🔒 exige JWT em todos os endpoints desta classe
public class VendasController {

    private final VendasService service;

    public VendasController(VendasService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Criar pedido de venda (status ABERTO)")
    public ResponseEntity<PedidoVendaDtoOut> criar() {
        return ResponseEntity.ok(service.criarPedido());
    }

    @PostMapping("/{id}/itens")
    @Operation(summary = "Adicionar item ao pedido")
    public ResponseEntity<PedidoVendaDtoOut> addItem(@PathVariable  UUID id,
                                                     @Validated @RequestBody ItemPedidoDtoIn in) {
        return ResponseEntity.ok(service.adicionarItem(id, in));
    }

    @PostMapping("/{id}/fechamento")
    @Operation(summary = "Fechar pedido de venda e dar baixa no estoque (por depósito)")
    public ResponseEntity<PedidoVendaDtoOut> fechar(@PathVariable  UUID id,
                                                    @RequestParam UUID depositoId) {
        return ResponseEntity.ok(service.fecharPedido(id, depositoId));
    }
}
