package br.com.pauloneto.loja.estoque.controller;

import br.com.pauloneto.loja.estoque.dto.ProdutoDtoIn;
import br.com.pauloneto.loja.estoque.dto.ProdutoDtoOut;
import br.com.pauloneto.loja.estoque.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "Operações de gestão de produtos")
@SecurityRequirement(name = "bearerAuth")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar produtos paginados")
    public Page<ProdutoDtoOut> listar(
            @ParameterObject
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return service.listar(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID")
    public ResponseEntity<ProdutoDtoOut> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo produto")
    public ResponseEntity<ProdutoDtoOut> criar(@Validated @RequestBody ProdutoDtoIn in) {
        return ResponseEntity.ok(service.criar(in));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto existente")
    public ResponseEntity<ProdutoDtoOut> atualizar(@PathVariable UUID id,
                                                   @Validated @RequestBody ProdutoDtoIn in) {
        return ResponseEntity.ok(service.atualizar(id, in));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir produto por ID")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
