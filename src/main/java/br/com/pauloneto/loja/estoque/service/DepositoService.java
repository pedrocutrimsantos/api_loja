// br.com.pauloneto.loja.estoque.service.DepositoService.java
package br.com.pauloneto.loja.estoque.service;

import br.com.pauloneto.loja.core.BusinessException;
import br.com.pauloneto.loja.estoque.dto.DepositoDtoIn;
import br.com.pauloneto.loja.estoque.dto.DepositoDtoOut;
import br.com.pauloneto.loja.estoque.entity.Deposito;
import br.com.pauloneto.loja.estoque.repository.DepositoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DepositoService {

    private final DepositoRepository repo;

    public DepositoService(DepositoRepository repo) {
        this.repo = repo;
    }

    public Page<DepositoDtoOut> listar(Pageable pageable, String nome) {
        Page<Deposito> page = (nome == null || nome.isBlank())
                ? repo.findAll(pageable)
                : repo.findByNomeContainingIgnoreCase(nome, pageable);
        return page.map(d -> new DepositoDtoOut(d.getId(), d.getNome()));
    }

    public DepositoDtoOut buscar(UUID id) {
        Deposito d = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Depósito não encontrado."));
        return new DepositoDtoOut(d.getId(), d.getNome());
    }

    @Transactional
    public DepositoDtoOut criar(DepositoDtoIn in) {
        if (repo.existsByNomeIgnoreCase(in.nome())) {
            throw new BusinessException("Já existe um depósito com esse nome.");
        }
        Deposito d = new Deposito();
        d.setNome(in.nome());
        return new DepositoDtoOut(repo.save(d).getId(), d.getNome());
    }

    @Transactional
    public DepositoDtoOut atualizar(UUID id, DepositoDtoIn in) {
        Deposito d = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Depósito não encontrado."));
        if (!d.getNome().equalsIgnoreCase(in.nome()) && repo.existsByNomeIgnoreCase(in.nome())) {
            throw new BusinessException("Já existe um depósito com esse nome.");
        }
        d.setNome(in.nome());
        return new DepositoDtoOut(repo.save(d).getId(), d.getNome());
    }

    @Transactional
    public void excluir(UUID id) {
        Deposito d = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Depósito não encontrado."));
        repo.delete(d);
    }
}
