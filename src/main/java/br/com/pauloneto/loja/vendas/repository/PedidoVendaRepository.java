package br.com.pauloneto.loja.vendas.repository;

import br.com.pauloneto.loja.vendas.entity.PedidoVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PedidoVendaRepository extends JpaRepository<PedidoVenda, UUID> {}
