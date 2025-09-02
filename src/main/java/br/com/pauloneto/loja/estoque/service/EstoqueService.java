package br.com.pauloneto.loja.estoque.service;

import br.com.pauloneto.loja.core.BusinessException;
import br.com.pauloneto.loja.estoque.dto.MovEstoqueDtoIn;
import br.com.pauloneto.loja.estoque.entity.*;
import br.com.pauloneto.loja.estoque.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.UUID;

@Service
public class EstoqueService {

    private final ProdutoRepository produtoRepository;
    private final DepositoRepository depositoRepository;
    private final PosicaoEstoqueRepository posicaoRepository;
    private final MovEstoqueRepository movRepository;

    public EstoqueService(ProdutoRepository produtoRepository, DepositoRepository depositoRepository,
                          PosicaoEstoqueRepository posicaoRepository, MovEstoqueRepository movRepository) {
        this.produtoRepository = produtoRepository;
        this.depositoRepository = depositoRepository;
        this.posicaoRepository = posicaoRepository;
        this.movRepository = movRepository;
    }

    @Transactional
    public void registrarMovimento(MovEstoqueDtoIn in) {
        MovEstoque.Tipo tipo = parseTipo(in.tipo());
        BigDecimal qtd = toQtd(in.quantidade());
        if (qtd.signum() <= 0) throw new BusinessException("Quantidade deve ser maior que zero.");

        Produto produto = produtoRepository.findById(in.produtoId())
                .orElseThrow(() -> new EntityNotFoundException("Produto inválido."));

        UUID depositoId = requireDeposito(in.depositoId());
        Deposito deposito = depositoRepository.findById(depositoId)
                .orElseThrow(() -> new EntityNotFoundException("Depósito inválido."));

        PosicaoEstoque pos = obterOuCriarPosicao(produto, deposito);

        switch (tipo) {
            case ENTRADA -> processarEntrada(pos, qtd, in);
            case SAIDA   -> processarSaida(pos, qtd, in);
            case TRANSFERENCIA  -> processarTransferencia(pos, qtd, in);
            default -> throw new BusinessException("Tipo de movimento inválido.");
        }
    }

    private void processarEntrada(PosicaoEstoque pos, BigDecimal qtd, MovEstoqueDtoIn in) {
        pos.setQtdDisponivel(pos.getQtdDisponivel().add(qtd));
        posicaoRepository.save(pos);
        registrarMov(pos, MovEstoque.Tipo.ENTRADA, qtd, in);
    }

    private void processarSaida(PosicaoEstoque pos, BigDecimal qtd, MovEstoqueDtoIn in) {
        if (pos.getQtdDisponivel().compareTo(qtd) < 0) {
            throw new BusinessException("Saldo insuficiente para saída. Disponível: " + pos.getQtdDisponivel());
        }
        pos.setQtdDisponivel(pos.getQtdDisponivel().subtract(qtd));
        posicaoRepository.save(pos);
        registrarMov(pos, MovEstoque.Tipo.SAIDA, qtd, in);
    }

    private void processarTransferencia(PosicaoEstoque pos, BigDecimal qtdInformada, MovEstoqueDtoIn in) {
        pos.setQtdDisponivel(pos.getQtdDisponivel().add(qtdInformada));
        if (pos.getQtdDisponivel().signum() < 0) {
            throw new BusinessException("Ajuste resultou em saldo negativo.");
        }
        posicaoRepository.save(pos);
        registrarMov(pos, MovEstoque.Tipo.TRANSFERENCIA, qtdInformada, in);
    }

    private void registrarMov(PosicaoEstoque pos, MovEstoque.Tipo tipo, BigDecimal qtd, MovEstoqueDtoIn in) {
        MovEstoque mov = new MovEstoque();
        mov.setPosicao(pos);
        mov.setTipo(tipo);
        mov.setQuantidade(qtd);
        mov.setObservacao(in.origem());
        mov.setCusto(toCusto(in.custo()));
        mov.setOrigem(in.origem());
        mov.setDocRef(in.docRef());
        movRepository.save(mov);
    }

    private PosicaoEstoque obterOuCriarPosicao(Produto produto, Deposito dep) {
        return posicaoRepository.findByProdutoAndDeposito(produto, dep)
                .orElseGet(() -> {
                    PosicaoEstoque p = new PosicaoEstoque();
                    p.setProduto(produto);
                    p.setDeposito(dep);
                    p.setQtdDisponivel(BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP));
                    p.setQtdReservada(BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP));
                    return posicaoRepository.save(p);
                });
    }

    private MovEstoque.Tipo parseTipo(String raw) {
        if (raw == null || raw.isBlank()) throw new BusinessException("Tipo de movimento é obrigatório.");
        try {
            return MovEstoque.Tipo.valueOf(raw.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new BusinessException("Tipo inválido. Use ENTRADA, SAIDA ou AJUSTE.");
        }
    }

    private UUID requireDeposito(UUID id) {
        if (id == null) throw new BusinessException("Depósito é obrigatório.");
        return id;
    }

    private BigDecimal toQtd(Double valor) {
        if (valor == null) throw new BusinessException("Quantidade é obrigatória.");
        return BigDecimal.valueOf(valor).setScale(3, RoundingMode.HALF_UP);
    }

    private BigDecimal toCusto(Double valor) {
        if (valor == null) return null;
        if (valor < 0) throw new BusinessException("Custo não pode ser negativo.");
        return BigDecimal.valueOf(valor).setScale(2, RoundingMode.HALF_UP);
    }
}
