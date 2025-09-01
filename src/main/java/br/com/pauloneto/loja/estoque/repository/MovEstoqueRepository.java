package br.com.pauloneto.loja.estoque.repository;
import br.com.pauloneto.loja.estoque.entity.MovEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovEstoqueRepository extends JpaRepository<MovEstoque, Long> {}