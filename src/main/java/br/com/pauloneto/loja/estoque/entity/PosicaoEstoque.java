package br.com.pauloneto.loja.estoque.entity;

import br.com.pauloneto.loja.core.BaseAuditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "posicao_estoque", uniqueConstraints = @UniqueConstraint(columnNames = {"produto_id","deposito_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PosicaoEstoque extends BaseAuditable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne(optional = false) @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne(optional = false) @JoinColumn(name = "deposito_id")
    private Deposito deposito;

    @Column(nullable = false)
    private Double qtdDisponivel = 0.0;

    @Column(nullable = false)
    private Double qtdReservada = 0.0;

    private Double minimo;
    private Double maximo;
}
