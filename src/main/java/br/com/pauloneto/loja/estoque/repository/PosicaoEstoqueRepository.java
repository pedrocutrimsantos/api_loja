package br.com.pauloneto.loja.estoque.repository;
import br.com.pauloneto.loja.estoque.entity.PosicaoEstoque;
import br.com.pauloneto.loja.estoque.entity.Deposito;
import br.com.pauloneto.loja.estoque.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PosicaoEstoqueRepository extends JpaRepository<PosicaoEstoque, Long> {
    Optional<PosicaoEstoque> findByProdutoAndDeposito(Produto p, Deposito d);
}