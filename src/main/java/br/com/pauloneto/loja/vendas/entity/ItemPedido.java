package br.com.pauloneto.loja.vendas.entity;

import br.com.pauloneto.loja.core.BaseAuditable;
import br.com.pauloneto.loja.estoque.entity.Produto;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "item_pedido")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ItemPedido extends BaseAuditable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pedido_id")
    private PedidoVenda pedido;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column(nullable = false)
    private Double quantidade;

    @Column(nullable = false)
    private Double precoUnitario;

    private Double desconto = 0.0;

    @Column(nullable = false)
    private Double totalItem;
}
