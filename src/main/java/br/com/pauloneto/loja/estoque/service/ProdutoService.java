package br.com.pauloneto.loja.estoque.service;

import br.com.pauloneto.loja.estoque.dto.ProdutoDtoIn;
import br.com.pauloneto.loja.estoque.dto.ProdutoDtoOut;
import br.com.pauloneto.loja.estoque.entity.Categoria;
import br.com.pauloneto.loja.estoque.entity.Produto;
import br.com.pauloneto.loja.estoque.repository.CategoriaRepository;
import br.com.pauloneto.loja.estoque.repository.ProdutoRepository;
import br.com.pauloneto.loja.core.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Page<ProdutoDtoOut> listar(Pageable pageable) {
        return produtoRepository.findAll(pageable).map(this::toOut);
    }

    public ProdutoDtoOut buscar(UUID id) {
        return produtoRepository.findById(id).map(this::toOut)
                .orElseThrow(() -> new BusinessException("Produto não encontrado"));
    }

    @Transactional
    public ProdutoDtoOut criar(ProdutoDtoIn in) {
        Categoria cat = categoriaRepository.findById(in.categoriaId())
                .orElseThrow(() -> new BusinessException("Categoria inválida"));
        Produto p = Produto.builder()
                .sku(in.sku())
                .ean(in.ean())
                .descricao(in.descricao())
                .ncm(in.ncm())
                .unidade(in.unidade())
                .peso(in.peso())
                .volume(in.volume())
                .categoria(cat)
                .marca(in.marca())
                .ativo(in.ativo() == null ? true : in.ativo())
                .build();
        p = produtoRepository.save(p);
        return toOut(p);
    }

    @Transactional
    public ProdutoDtoOut atualizar(UUID id, ProdutoDtoIn in) {
        Produto p = produtoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Produto não encontrado"));
        Categoria cat = categoriaRepository.findById(in.categoriaId())
                .orElseThrow(() -> new BusinessException("Categoria inválida"));
        p.setSku(in.sku());
        p.setEan(in.ean());
        p.setDescricao(in.descricao());
        p.setNcm(in.ncm());
        p.setUnidade(in.unidade());
        p.setPeso(in.peso());
        p.setVolume(in.volume());
        p.setCategoria(cat);
        p.setMarca(in.marca());
        p.setAtivo(in.ativo() == null ? true : in.ativo());
        return toOut(produtoRepository.save(p));
    }

    @Transactional
    public void excluir(UUID id) {
        if (!produtoRepository.existsById(id)) throw new BusinessException("Produto não encontrado");
        produtoRepository.deleteById(id);
    }

    private ProdutoDtoOut toOut(Produto p) {
        return new ProdutoDtoOut(
                p.getId(), p.getSku(), p.getEan(), p.getDescricao(), p.getNcm(), p.getUnidade(),
                p.getPeso(), p.getVolume(), p.getCategoria() != null ? p.getCategoria().getId() : null,
                p.getMarca(), p.getAtivo()
        );
    }
}
