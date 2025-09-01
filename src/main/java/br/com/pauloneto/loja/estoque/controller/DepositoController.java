// br.com.pauloneto.loja.estoque.controller.DepositoController.java
package br.com.pauloneto.loja.estoque.controller;

import br.com.pauloneto.loja.core.api.ApiResponse;
import br.com.pauloneto.loja.estoque.dto.DepositoDtoIn;
import br.com.pauloneto.loja.estoque.dto.DepositoDtoOut;
import br.com.pauloneto.loja.estoque.service.DepositoService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Depósitos")
@RestController
@RequestMapping("/api/depositos")
public class DepositoController {

    private final DepositoService service;
    public DepositoController(DepositoService service) { this.service = service; }

    @Operation(summary = "Listar depósitos")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<DepositoDtoOut>>> listar(Pageable pageable,
                                                                    @RequestParam(required = false) String nome) {
        return ResponseEntity.ok(ApiResponse.ok("Depósitos listados", service.listar(pageable, nome)));
    }

    @Operation(summary = "Buscar depósito por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepositoDtoOut>> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok("Depósito encontrado", service.buscar(id)));
    }

    @Operation(summary = "Criar depósito")
    @PostMapping
    public ResponseEntity<ApiResponse<DepositoDtoOut>> criar(@Valid @RequestBody DepositoDtoIn in) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Depósito criado", service.criar(in)));
    }

    @Operation(summary = "Atualizar depósito")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DepositoDtoOut>> atualizar(@PathVariable UUID id, @Valid @RequestBody DepositoDtoIn in) {
        return ResponseEntity.ok(ApiResponse.ok("Depósito atualizado", service.atualizar(id, in)));
    }

    @Operation(summary = "Excluir depósito")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> excluir(@PathVariable UUID id) {
        service.excluir(id);
        return ResponseEntity.ok(ApiResponse.ok("Depósito excluído", null));
    }
}
