package br.com.pauloneto.loja.estoque.service;

import br.com.pauloneto.loja.core.BusinessException;
import br.com.pauloneto.loja.estoque.dto.MovEstoqueDtoIn;
import br.com.pauloneto.loja.estoque.entity.*;
import br.com.pauloneto.loja.estoque.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Produto produto = produtoRepository.findById(in.produtoId())
                .orElseThrow(() -> new BusinessException("Produto inválido"));
        Deposito deposito = depositoRepository.findById(in.depositoId())
                .orElseThrow(() -> new BusinessException("Depósito inválido"));

        PosicaoEstoque pos = posicaoRepository.findByProdutoAndDeposito(produto, deposito)
                .orElse(PosicaoEstoque.builder().produto(produto).deposito(deposito).qtdDisponivel(0.0).qtdReservada(0.0).build());

        MovEstoque.TipoMovimento tipo = MovEstoque.TipoMovimento.valueOf(in.tipo());
        double qtd = in.quantidade();

        switch (tipo) {
            case ENTRADA -> pos.setQtdDisponivel(pos.getQtdDisponivel() + qtd);
            case SAIDA -> {
                if (pos.getQtdDisponivel() < qtd) throw new BusinessException("Estoque insuficiente");
                pos.setQtdDisponivel(pos.getQtdDisponivel() - qtd);
            }
            case AJUSTE -> pos.setQtdDisponivel(pos.getQtdDisponivel() + qtd);
        }

        posicaoRepository.save(pos);

        MovEstoque mov = MovEstoque.builder()
                .produto(produto)
                .deposito(deposito)
                .tipo(tipo)
                .quantidade(qtd)
                .custo(in.custo())
                .origem(in.origem())
                .docRef(in.docRef())
                .build();
        movRepository.save(mov);
    }
}
