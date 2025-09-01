package br.com.pauloneto.loja.estoque.entity;

import br.com.pauloneto.loja.core.BaseAuditable;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "mov_estoque")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovEstoque extends BaseAuditable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne(optional = false) @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne(optional = false) @JoinColumn(name = "deposito_id")
    private Deposito deposito;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TipoMovimento tipo;

    @Column(nullable = false)
    private Double quantidade;

    private Double custo;

    @Column(length = 50)
    private String origem; // VENDA, AJUSTE, COMPRA, etc.

    @Column(length = 60)
    private String docRef;

    @Column(nullable = false)
    private LocalDateTime data = LocalDateTime.now();

    public enum TipoMovimento { ENTRADA, SAIDA, AJUSTE }
}
