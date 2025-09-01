// br.com.pauloneto.loja.estoque.service.CategoriaService.java
package br.com.pauloneto.loja.estoque.service;

import br.com.pauloneto.loja.core.BusinessException;
import br.com.pauloneto.loja.estoque.dto.CategoriaDtoIn;
import br.com.pauloneto.loja.estoque.dto.CategoriaDtoOut;
import br.com.pauloneto.loja.estoque.entity.Categoria;
import br.com.pauloneto.loja.estoque.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CategoriaService {

    private final CategoriaRepository repo;

    public CategoriaService(CategoriaRepository repo) {
        this.repo = repo;
    }

    public Page<CategoriaDtoOut> listar(Pageable pageable, String nome) {
        Page<Categoria> page = (nome == null || nome.isBlank())
                ? repo.findAll(pageable)
                : repo.findByNomeContainingIgnoreCase(nome, pageable);
        return page.map(this::toOut);
    }

    public CategoriaDtoOut buscar(UUID id) {
        return toOut(findById(id));
    }

    @Transactional
    public CategoriaDtoOut criar(CategoriaDtoIn in) {
        if (repo.existsByNomeIgnoreCase(in.nome())) {
            throw new BusinessException("Já existe uma categoria com esse nome.");
        }
        Categoria c = new Categoria();
        c.setNome(in.nome());
        c.setAtiva(in.ativa() == null ? Boolean.TRUE : in.ativa());
        return toOut(repo.save(c));
    }

    @Transactional
    public CategoriaDtoOut atualizar(UUID id, CategoriaDtoIn in) {
        Categoria c = findById(id);
        if (!c.getNome().equalsIgnoreCase(in.nome()) && repo.existsByNomeIgnoreCase(in.nome())) {
            throw new BusinessException("Já existe uma categoria com esse nome.");
        }
        c.setNome(in.nome());
        c.setAtiva(in.ativa() == null ? c.getAtiva() : in.ativa());
        return toOut(repo.save(c));
    }

    @Transactional
    public void excluir(UUID id) {
        Categoria c = findById(id);
        repo.delete(c);
    }

    private Categoria findById(UUID id) {
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada."));
    }

    private CategoriaDtoOut toOut(Categoria c) {
        return new CategoriaDtoOut(c.getId(), c.getNome(), c.getAtiva());
    }
}
