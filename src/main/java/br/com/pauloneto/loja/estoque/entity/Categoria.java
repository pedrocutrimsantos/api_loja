package br.com.pauloneto.loja.estoque.entity;

import br.com.pauloneto.loja.core.BaseAuditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "categoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categoria extends BaseAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "nome", nullable = false, unique = true, length = 120)
    private String nome;

    @Column(name = "ativa", nullable = false)
    private Boolean ativa = true;
}
