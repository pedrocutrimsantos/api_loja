// br.com.pauloneto.loja.estoque.service.PosicaoEstoqueService.java
package br.com.pauloneto.loja.estoque.service;

import br.com.pauloneto.loja.estoque.dto.PosicaoEstoqueDtoOut;
import br.com.pauloneto.loja.estoque.entity.PosicaoEstoque;
import br.com.pauloneto.loja.estoque.repository.PosicaoEstoqueRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PosicaoEstoqueService {

    private final PosicaoEstoqueRepository repo;

    public PosicaoEstoqueService(PosicaoEstoqueRepository repo) {
        this.repo = repo;
    }

    public Page<PosicaoEstoqueDtoOut> listar(Pageable pageable, UUID produtoId, UUID depositoId) {
        Specification<PosicaoEstoque> spec = Specification.where(null);
        if (produtoId != null) {
            spec = spec.and((root, q, cb) -> cb.equal(root.get("produto").get("id"), produtoId));
        }
        if (depositoId != null) {
            spec = spec.and((root, q, cb) -> cb.equal(root.get("deposito").get("id"), depositoId));
        }
        Page<PosicaoEstoque> page = repo.findAll(spec, pageable);
        return page.map(this::toOut);
    }

    public PosicaoEstoqueDtoOut detalhar(UUID id) {
        PosicaoEstoque pe = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Posição não encontrada."));
        return toOut(pe);
    }

    private PosicaoEstoqueDtoOut toOut(PosicaoEstoque pe) {
        return new PosicaoEstoqueDtoOut(
                pe.getId(),
                pe.getProduto().getId(),
                pe.getDeposito().getId(),
                pe.getQtdDisponivel(),
                pe.getQtdReservada(),
                pe.getMinimo(),
                pe.getMaximo()
        );
    }
}
