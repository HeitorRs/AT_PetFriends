package br.edu.infnet.pedidos.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor@NoArgsConstructor@Data
public class ItemPedidoMensagem {

    private Integer quantity;

    private Long productId;
}
