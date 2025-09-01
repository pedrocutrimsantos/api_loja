// br.com.pauloneto.loja.estoque.controller.CategoriaController.java
package br.com.pauloneto.loja.estoque.controller;

import br.com.pauloneto.loja.core.api.ApiResponse;
import br.com.pauloneto.loja.estoque.dto.CategoriaDtoIn;
import br.com.pauloneto.loja.estoque.dto.CategoriaDtoOut;
import br.com.pauloneto.loja.estoque.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Categorias")
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService service;
    public CategoriaController(CategoriaService service) { this.service = service; }

    @Operation(summary = "Listar categorias paginadas")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<CategoriaDtoOut>>> listar(Pageable pageable,
                                                                     @RequestParam(required = false) String nome) {
        Page<CategoriaDtoOut> page = service.listar(pageable, nome);
        return ResponseEntity.ok(ApiResponse.ok("Categorias listadas com sucesso", page));
    }

    @Operation(summary = "Buscar categoria por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoriaDtoOut>> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok("Categoria encontrada", service.buscar(id)));
    }

    @Operation(summary = "Criar categoria")
    @PostMapping
    public ResponseEntity<ApiResponse<CategoriaDtoOut>> criar(@Valid @RequestBody CategoriaDtoIn in) {
        CategoriaDtoOut out = service.criar(in);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created("Categoria criada", out));
    }

    @Operation(summary = "Atualizar categoria")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoriaDtoOut>> atualizar(@PathVariable UUID id, @Valid @RequestBody CategoriaDtoIn in) {
        return ResponseEntity.ok(ApiResponse.ok("Categoria atualizada", service.atualizar(id, in)));
    }

    @Operation(summary = "Excluir categoria")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> excluir(@PathVariable UUID id) {
        service.excluir(id);
        return ResponseEntity.ok(ApiResponse.ok("Categoria excluída", null));
    }
}
