package br.com.pauloneto.loja.estoque.entity;

import br.com.pauloneto.loja.core.BaseAuditable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "posicao_estoque", uniqueConstraints = @UniqueConstraint(columnNames = {"produto_id","deposito_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PosicaoEstoque extends BaseAuditable {
    @Id
    @GeneratedValue
    @org.hibernate.annotations.UuidGenerator
    private UUID id;

    @ManyToOne(optional = false) @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne(optional = false) @JoinColumn(name = "deposito_id")
    private Deposito deposito;

    @Column(nullable = false, precision = 19, scale = 3)
    private BigDecimal qtdDisponivel = BigDecimal.ZERO;

    @Column(nullable = false, precision = 19, scale = 3)
    private BigDecimal qtdReservada = BigDecimal.ZERO;

    @Column(precision = 19, scale = 3)
    private BigDecimal minimo;

    @Column(precision = 19, scale = 3)
    private BigDecimal maximo;
}
