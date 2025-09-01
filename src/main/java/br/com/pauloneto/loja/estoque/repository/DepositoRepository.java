// br.com.pauloneto.loja.estoque.repository.DepositoRepository.java
package br.com.pauloneto.loja.estoque.repository;

import br.com.pauloneto.loja.estoque.entity.Deposito;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepositoRepository extends JpaRepository<Deposito, UUID> {
    Page<Deposito> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    boolean existsByNomeIgnoreCase(String nome);
}
