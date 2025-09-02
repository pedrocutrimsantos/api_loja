// br.com.pauloneto.loja.vendas.service.VendasService.java
package br.com.pauloneto.loja.vendas.service;

import br.com.pauloneto.loja.core.BusinessException;
import br.com.pauloneto.loja.estoque.entity.Deposito;
import br.com.pauloneto.loja.estoque.entity.MovEstoque;
import br.com.pauloneto.loja.estoque.entity.PosicaoEstoque;
import br.com.pauloneto.loja.estoque.entity.Produto;
import br.com.pauloneto.loja.estoque.repository.DepositoRepository;
import br.com.pauloneto.loja.estoque.repository.MovEstoqueRepository;
import br.com.pauloneto.loja.estoque.repository.PosicaoEstoqueRepository;
import br.com.pauloneto.loja.estoque.repository.ProdutoRepository;
import br.com.pauloneto.loja.vendas.dto.ItemPedidoDtoIn;
import br.com.pauloneto.loja.vendas.dto.ItemPedidoDtoOut;
import br.com.pauloneto.loja.vendas.dto.PedidoVendaDtoOut;
import br.com.pauloneto.loja.vendas.entity.ItemPedido;
import br.com.pauloneto.loja.vendas.entity.PedidoVenda;
import br.com.pauloneto.loja.vendas.repository.ItemPedidoRepository;
import br.com.pauloneto.loja.vendas.repository.PedidoVendaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Service
public class VendasService {

    private final PedidoVendaRepository pedidoRepo;
    private final ItemPedidoRepository itemRepo;
    private final ProdutoRepository produtoRepo;
    private final DepositoRepository depositoRepo;
    private final PosicaoEstoqueRepository posicaoRepo;
    private final MovEstoqueRepository movRepo;

    public VendasService(PedidoVendaRepository pedidoRepo,
                         ItemPedidoRepository itemRepo,
                         ProdutoRepository produtoRepo,
                         DepositoRepository depositoRepo,
                         PosicaoEstoqueRepository posicaoRepo,
                         MovEstoqueRepository movRepo) {
        this.pedidoRepo = pedidoRepo;
        this.itemRepo = itemRepo;
        this.produtoRepo = produtoRepo;
        this.depositoRepo = depositoRepo;
        this.posicaoRepo = posicaoRepo;
        this.movRepo = movRepo;
    }

    @Transactional
    public PedidoVendaDtoOut criarPedido() {
        PedidoVenda p = PedidoVenda.builder().status("ABERTO").build();
        p = pedidoRepo.save(p);
        return toOut(p);
    }

    @Transactional
    public PedidoVendaDtoOut adicionarItem(UUID pedidoId, ItemPedidoDtoIn in) {
        PedidoVenda p = pedidoRepo.findById(pedidoId)
                .orElseThrow(() -> new BusinessException("Pedido não encontrado"));
        if (!"ABERTO".equals(p.getStatus())) throw new BusinessException("Pedido não está aberto");

        Produto prod = produtoRepo.findById(in.produtoId())
                .orElseThrow(() -> new BusinessException("Produto inválido"));

        double quantidade = in.quantidade();
        double precoUnit = in.precoUnitario();
        double desconto = in.desconto() == null ? 0.0 : in.desconto();
        double totalItem = (precoUnit * quantidade) - desconto;

        ItemPedido item = ItemPedido.builder()
                .pedido(p)
                .produto(prod)
                .quantidade(quantidade)
                .precoUnitario(precoUnit)
                .desconto(desconto)
                .totalItem(totalItem)
                .build();
        itemRepo.save(item);

        p.getItens().add(item);
        recomputarTotais(p);
        return toOut(pedidoRepo.save(p));
    }

    @Transactional
    public PedidoVendaDtoOut fecharPedido(UUID pedidoId, UUID depositoId) {
        PedidoVenda p = pedidoRepo.findById(pedidoId)
                .orElseThrow(() -> new BusinessException("Pedido não encontrado"));
        if (!"ABERTO".equals(p.getStatus())) throw new BusinessException("Pedido não está aberto");

        Deposito dep = depositoRepo.findById(depositoId)
                .orElseThrow(() -> new BusinessException("Depósito inválido"));

        for (ItemPedido it : p.getItens()) {
            Produto produto = it.getProduto();
            PosicaoEstoque pos = posicaoRepo.findByProdutoAndDeposito(produto, dep)
                    .orElseThrow(() -> new BusinessException(
                            "Posição de estoque não encontrada para o produto " + produto.getId()));

            BigDecimal qtdDisponivel = nz(pos.getQtdDisponivel());
            BigDecimal qtdSaida = bd(it.getQuantidade(), 3);

            if (qtdDisponivel.compareTo(qtdSaida) < 0) {
                throw new BusinessException("Estoque insuficiente para produto " + produto.getId()
                        + ". Disponível: " + qtdDisponivel + ", necessário: " + qtdSaida);
            }

            pos.setQtdDisponivel(qtdDisponivel.subtract(qtdSaida));
            posicaoRepo.save(pos);

            MovEstoque mov = new MovEstoque();
            mov.setPosicao(pos);
            mov.setTipo(MovEstoque.Tipo.SAIDA);
            mov.setQuantidade(qtdSaida);
            mov.setObservacao("Baixa por fechamento do pedido " + p.getId());
            mov.setOrigem("VENDA");
            mov.setDocRef(p.getId().toString());
            movRepo.save(mov);
        }

        p.setStatus("FECHADO");
        recomputarTotais(p);
        return toOut(pedidoRepo.save(p));
    }

    private void recomputarTotais(PedidoVenda p) {
        BigDecimal bruto = p.getItens().stream()
                .map(i -> bd(i.getPrecoUnitario(), 2).multiply(bd(i.getQuantidade(), 3)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal desc = p.getItens().stream()
                .map(i -> bd(i.getDesconto() == null ? 0.0 : i.getDesconto(), 2))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal liquido = bruto.subtract(desc);

        p.setTotalBruto(bruto.doubleValue());
        p.setDescontoTotal(desc.doubleValue());
        p.setTotalLiquido(liquido.doubleValue());
    }

    private BigDecimal bd(Double v, int scale) {
        return BigDecimal.valueOf(v == null ? 0.0 : v).setScale(scale, RoundingMode.HALF_UP);
    }

    private BigDecimal nz(BigDecimal v) {
        return v == null ? BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP) : v;
    }

    private PedidoVendaDtoOut toOut(PedidoVenda p) {
        List<ItemPedidoDtoOut> itens = p.getItens().stream()
                .map(i -> new ItemPedidoDtoOut(
                        i.getId(),
                        i.getProduto().getId(),
                        i.getQuantidade(),
                        i.getPrecoUnitario(),
                        i.getDesconto(),
                        i.getTotalItem()))
                .toList();
        return new PedidoVendaDtoOut(
                p.getId(),
                p.getStatus(),
                p.getTotalBruto(),
                p.getDescontoTotal(),
                p.getTotalLiquido(),
                itens);
    }
}
