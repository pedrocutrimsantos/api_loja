package br.com.pauloneto.loja.estoque.entity;

import br.com.pauloneto.loja.core.BaseAuditable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "mov_estoque")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class MovEstoque extends BaseAuditable {

    @Id
    @GeneratedValue
    @org.hibernate.annotations.UuidGenerator
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "posicao_id", nullable = false)
    private PosicaoEstoque posicao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private Tipo tipo; // ENTRADA, SAIDA, TRANSFERENCIA (ou AJUSTE se preferir)

    @Column(nullable = false, precision = 19, scale = 3)
    private BigDecimal quantidade;

    @Column(length = 255)
    private String observacao;

    @Column(precision = 19, scale = 2)
    private BigDecimal custo;

    @Column(length = 50)
    private String origem;

    @Column(length = 60)
    private String docRef;

    @Column(nullable = false)
    private LocalDateTime data = LocalDateTime.now();

    public enum Tipo { ENTRADA, SAIDA, TRANSFERENCIA }
}
