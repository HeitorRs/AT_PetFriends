package br.edu.infnet.pedidos.eventos;

import br.edu.infnet.pedidos.domain.ItemPedido;
import br.edu.infnet.pedidos.domain.ItemPedidoMensagem;
import br.edu.infnet.pedidos.domain.Pedido;
import br.edu.infnet.pedidos.domain.PedidoStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor@NoArgsConstructor@Data@Builder
public class EstadoPedidoMudou implements Serializable {
    
    private Long idPedido;
    private PedidoStatus estado;
    private Date momento;
    private List<ItemPedidoMensagem> itens;


    public EstadoPedidoMudou(Long idPedido, PedidoStatus estado) {
        this.idPedido = idPedido;
        this.estado = estado;
        this.momento = new Date();
    }

    public EstadoPedidoMudou(Long idPedido, PedidoStatus estado, Date momento) {
        this.idPedido = idPedido;
        this.estado = estado;
        this.momento = momento;
    }

    public EstadoPedidoMudou(Long idPedido,List<ItemPedidoMensagem> itens, PedidoStatus status) {
        this.idPedido = idPedido;
        this.itens = itens;
        this.estado = status;
        this.momento = new Date();
    }

    @Override
    public String toString() {
        return "EstadoPedidoMudou{" + "idPedido=" + idPedido + ", estado=" + estado + ", momento=" + momento + '}';
    }
}
