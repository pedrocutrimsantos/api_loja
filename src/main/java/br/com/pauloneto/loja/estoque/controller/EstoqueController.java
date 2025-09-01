package br.com.pauloneto.loja.estoque.controller;

import br.com.pauloneto.loja.estoque.dto.MovEstoqueDtoIn;
import br.com.pauloneto.loja.estoque.entity.Deposito;
import br.com.pauloneto.loja.estoque.entity.PosicaoEstoque;
import br.com.pauloneto.loja.estoque.repository.DepositoRepository;
import br.com.pauloneto.loja.estoque.repository.PosicaoEstoqueRepository;
import br.com.pauloneto.loja.estoque.service.EstoqueService;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoques")
@Tag(name = "Estoque", description = "Operações relacionadas ao controle de estoque")
@SecurityRequirement(name = "bearerAuth")
public class EstoqueController {

    private final EstoqueService service;
    private final PosicaoEstoqueRepository posicaoRepo;
    private final DepositoRepository depositoRepo;

    public EstoqueController(EstoqueService service,
                             PosicaoEstoqueRepository posicaoRepo,
                             DepositoRepository depositoRepo) {
        this.service = service;
        this.posicaoRepo = posicaoRepo;
        this.depositoRepo = depositoRepo;
    }

    @GetMapping("/posicao")
    @Operation(summary = "Consultar posição de estoque")
    public List<PosicaoEstoque> posicao() {
        return posicaoRepo.findAll();
    }

    @GetMapping("/depositos")
    @Operation(summary = "Listar depósitos")
    public List<Deposito> depositos() {
        return depositoRepo.findAll();
    }

    @PostMapping("/movimentos")
    @Operation(summary = "Registrar movimento de estoque")
    public ResponseEntity<Void> movimentar(@Validated @RequestBody MovEstoqueDtoIn in) {
        service.registrarMovimento(in);
        return ResponseEntity.ok().build();
    }
}
