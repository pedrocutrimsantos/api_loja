package br.com.pauloneto.loja.estoque.entity;

import br.com.pauloneto.loja.core.BaseAuditable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "preco")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Preco extends BaseAuditable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    private Double custo;
    private Double margem;
    private Double precoVista;
    private Double precoPrazo;

    private LocalDateTime inicioVigencia;
    private LocalDateTime fimVigencia;
}
