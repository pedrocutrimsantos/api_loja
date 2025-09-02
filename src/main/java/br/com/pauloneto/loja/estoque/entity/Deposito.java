package br.com.pauloneto.loja.estoque.entity;

import br.com.pauloneto.loja.core.BaseAuditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "deposito")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deposito extends BaseAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "nome", nullable = false, unique = true, length = 120)
    private String nome;
}
