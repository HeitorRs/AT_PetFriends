package br.edu.infnet.pedidos.infra.service;

import br.edu.infnet.pedidos.domain.ItemPedidoMensagem;
import br.edu.infnet.pedidos.domain.Pedido;
import br.edu.infnet.pedidos.eventos.EstadoPedidoMudou;
import br.edu.infnet.pedidos.infra.repository.PedidoRepository;
import br.edu.infnet.rabbitMQ.TransporteProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransporteService {
    private final TransporteProducer transporteProducer;
    private final PedidoRepository pedidoRepository;

    public void processarTransporte(Pedido pedido){
        try {
            transporteProducer.enviar(new EstadoPedidoMudou(pedido.getId(), pedido.getStatus()));
        }catch (JsonProcessingException e) {
            pedido.erroPedido();
        }
        pedido.transportandoPedido();
        pedidoRepository.save(pedido);
    }
}
