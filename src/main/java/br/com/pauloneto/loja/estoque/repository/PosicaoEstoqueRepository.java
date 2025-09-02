// br.com.pauloneto.loja.estoque.repository.PosicaoEstoqueRepository.java
package br.com.pauloneto.loja.estoque.repository;

import br.com.pauloneto.loja.estoque.entity.PosicaoEstoque;
import br.com.pauloneto.loja.estoque.entity.Deposito;
import br.com.pauloneto.loja.estoque.entity.Produto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PosicaoEstoqueRepository
        extends JpaRepository<PosicaoEstoque, UUID>, JpaSpecificationExecutor<PosicaoEstoque> {

    Optional<PosicaoEstoque> findByProdutoAndDeposito(Produto p, Deposito d);

}
