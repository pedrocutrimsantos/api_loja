package br.com.pauloneto.loja.estoque.controller;

import br.com.pauloneto.loja.core.api.ApiResponse;
import br.com.pauloneto.loja.estoque.dto.PosicaoEstoqueDtoOut;
import br.com.pauloneto.loja.estoque.service.PosicaoEstoqueService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Posição de Estoque")
@RestController
@RequestMapping("/api/posicoes-estoque")
public class PosicaoEstoqueController {

    private final PosicaoEstoqueService service;
    public PosicaoEstoqueController(PosicaoEstoqueService service) { this.service = service; }

    @Operation(summary = "Listar posições de estoque (paginado)")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<PosicaoEstoqueDtoOut>>> listar(Pageable pageable,
                                                                          @RequestParam(required = false) UUID produtoId,
                                                                          @RequestParam(required = false) UUID depositoId) {
        return ResponseEntity.ok(ApiResponse.ok("Posições listadas", service.listar(pageable, produtoId, depositoId)));
    }

    @Operation(summary = "Detalhar posição por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PosicaoEstoqueDtoOut>> detalhar(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok("Posição encontrada", service.detalhar(id)));
    }
}
