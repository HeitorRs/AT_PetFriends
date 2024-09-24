package br.edu.infnet.pedidos.infra.service;

import br.edu.infnet.pedidos.domain.ItemPedidoMensagem;
import br.edu.infnet.pedidos.domain.Pedido;
import br.edu.infnet.pedidos.domain.PedidoStatus;
import br.edu.infnet.pedidos.eventos.EstadoPedidoMudou;
import br.edu.infnet.pedidos.infra.repository.PedidoRepository;
import br.edu.infnet.rabbitMQ.AlmoxafrifadoProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlmoxarifadoService {
    private final AlmoxafrifadoProducer almoxafrifadoProducer;
    private final PedidoRepository pedidoRepository;

    public void processarAlmoxarifado(Pedido pedido){
        try {
            List<ItemPedidoMensagem> itens = pedido.getItemList().stream()
                    .map(item -> new ItemPedidoMensagem(item.getQuantity(), item.getProductId()))
                    .toList();
            almoxafrifadoProducer.enviar(new EstadoPedidoMudou(pedido.getId(),itens,pedido.getStatus()));
        }catch (JsonProcessingException e) {
            pedido.erroPedido();
        }
        pedido.enviarPedido();
        pedidoRepository.save(pedido);
    }
}
