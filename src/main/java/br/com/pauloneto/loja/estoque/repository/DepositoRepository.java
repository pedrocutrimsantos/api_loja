package br.com.pauloneto.loja.estoque.repository;
import br.com.pauloneto.loja.estoque.entity.Deposito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DepositoRepository extends JpaRepository<Deposito, UUID> {}