package br.com.pauloneto.loja.estoque.entity;

import br.com.pauloneto.loja.core.BaseAuditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto extends BaseAuditable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(nullable = false, unique = true, length = 60)
    private String sku;

    @Column(length = 14)
    private String ean;

    @Column(nullable = false, length = 200)
    private String descricao;

    @Column(length = 8)
    private String ncm;

    @Column(length = 10)
    private String unidade;

    private Double peso;
    private Double volume;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Column(length = 120)
    private String marca;

    @Column(nullable = false)
    private Boolean ativo = true;
}
