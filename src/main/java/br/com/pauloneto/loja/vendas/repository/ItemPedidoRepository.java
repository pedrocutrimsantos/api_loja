package br.com.pauloneto.loja.vendas.repository;

import br.com.pauloneto.loja.vendas.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {}
