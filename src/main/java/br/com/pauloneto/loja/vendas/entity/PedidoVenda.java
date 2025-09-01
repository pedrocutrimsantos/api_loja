package br.com.pauloneto.loja.vendas.entity;

import br.com.pauloneto.loja.core.BaseAuditable;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pedido_venda")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PedidoVenda extends BaseAuditable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(length = 20, nullable = false)
    private String status; // ABERTO, FECHADO, CANCELADO

    @Column(nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    private Double totalBruto = 0.0;
    private Double descontoTotal = 0.0;
    private Double totalLiquido = 0.0;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();
}
