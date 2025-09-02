package br.com.pauloneto.loja.estoque.repository;

import br.com.pauloneto.loja.estoque.entity.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
    Page<Categoria> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    boolean existsByNomeIgnoreCase(String nome);
}
