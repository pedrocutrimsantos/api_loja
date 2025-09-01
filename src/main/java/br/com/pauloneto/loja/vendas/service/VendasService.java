package br.com.pauloneto.loja.vendas.service;

import br.com.pauloneto.loja.core.BusinessException;
import br.com.pauloneto.loja.estoque.entity.Deposito;
import br.com.pauloneto.loja.estoque.entity.MovEstoque;
import br.com.pauloneto.loja.estoque.entity.PosicaoEstoque;
import br.com.pauloneto.loja.estoque.entity.Produto;
import br.com.pauloneto.loja.estoque.repository.DepositoRepository;
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

import java.util.List;
import java.util.UUID;

@Service
public class VendasService {

    private final PedidoVendaRepository pedidoRepo;
    private final ItemPedidoRepository itemRepo;
    private final ProdutoRepository produtoRepo;
    private final DepositoRepository depositoRepo;
    private final PosicaoEstoqueRepository posicaoRepo;

    public VendasService(PedidoVendaRepository pedidoRepo, ItemPedidoRepository itemRepo, ProdutoRepository produtoRepo,
                         DepositoRepository depositoRepo, PosicaoEstoqueRepository posicaoRepo) {
        this.pedidoRepo = pedidoRepo;
        this.itemRepo = itemRepo;
        this.produtoRepo = produtoRepo;
        this.depositoRepo = depositoRepo;
        this.posicaoRepo = posicaoRepo;
    }

    @Transactional
    public PedidoVendaDtoOut criarPedido() {
        PedidoVenda p = PedidoVenda.builder().status("ABERTO").build();
        p = pedidoRepo.save(p);
        return toOut(p);
    }

    @Transactional
    public PedidoVendaDtoOut adicionarItem(UUID pedidoId, ItemPedidoDtoIn in) {
        PedidoVenda p = pedidoRepo.findById(pedidoId).orElseThrow(() -> new BusinessException("Pedido não encontrado"));
        if (!"ABERTO".equals(p.getStatus())) throw new BusinessException("Pedido não está aberto");
        Produto prod = produtoRepo.findById(in.produtoId()).orElseThrow(() -> new BusinessException("Produto inválido"));

        ItemPedido item = ItemPedido.builder()
                .pedido(p)
                .produto(prod)
                .quantidade(in.quantidade())
                .precoUnitario(in.precoUnitario())
                .desconto(in.desconto() == null ? 0.0 : in.desconto())
                .totalItem((in.precoUnitario() * in.quantidade()) - (in.desconto() == null ? 0.0 : in.desconto()))
                .build();
        itemRepo.save(item);

        p.getItens().add(item);
        recomputarTotais(p);
        return toOut(pedidoRepo.save(p));
    }

    @Transactional
    public PedidoVendaDtoOut fecharPedido(UUID pedidoId, UUID depositoId) {
        PedidoVenda p = pedidoRepo.findById(pedidoId).orElseThrow(() -> new BusinessException("Pedido não encontrado"));
        if (!"ABERTO".equals(p.getStatus())) throw new BusinessException("Pedido não está aberto");

        Deposito dep = depositoRepo.findById(depositoId).orElseThrow(() -> new BusinessException("Depósito inválido"));

        // baixa do estoque
        for (ItemPedido it : p.getItens()) {
            PosicaoEstoque pos = posicaoRepo.findByProdutoAndDeposito(it.getProduto(), dep)
                    .orElseThrow(() -> new BusinessException("Posição de estoque não encontrada para o produto " + it.getProduto().getId()));
            if (pos.getQtdDisponivel() < it.getQuantidade()) throw new BusinessException("Estoque insuficiente para produto " + it.getProduto().getId());
            pos.setQtdDisponivel(pos.getQtdDisponivel() - it.getQuantidade());
            posicaoRepo.save(pos);

        }

        p.setStatus("FECHADO");
        recomputarTotais(p);
        return toOut(pedidoRepo.save(p));
    }

    private void recomputarTotais(PedidoVenda p) {
        double bruto = p.getItens().stream().mapToDouble(i -> i.getPrecoUnitario() * i.getQuantidade()).sum();
        double desc = p.getItens().stream().mapToDouble(i -> i.getDesconto() == null ? 0.0 : i.getDesconto()).sum();
        p.setTotalBruto(bruto);
        p.setDescontoTotal(desc);
        p.setTotalLiquido(bruto - desc);
    }

    private PedidoVendaDtoOut toOut(PedidoVenda p) {
        List<ItemPedidoDtoOut> itens = p.getItens().stream()
                .map(i -> new ItemPedidoDtoOut(i.getId(), i.getProduto().getId(), i.getQuantidade(), i.getPrecoUnitario(), i.getDesconto(), i.getTotalItem()))
                .toList();
        return new PedidoVendaDtoOut(p.getId(), p.getStatus(), p.getTotalBruto(), p.getDescontoTotal(), p.getTotalLiquido(), itens);
    }
}
