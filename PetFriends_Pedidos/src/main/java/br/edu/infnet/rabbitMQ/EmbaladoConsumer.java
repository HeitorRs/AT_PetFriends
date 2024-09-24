package br.edu.infnet.rabbitMQ;

import br.edu.infnet.pedidos.domain.Pedido;
import br.edu.infnet.pedidos.eventos.EstadoPedidoMudou;
import br.edu.infnet.pedidos.infra.repository.PedidoRepository;
import br.edu.infnet.pedidos.infra.service.PedidoService;
import br.edu.infnet.pedidos.infra.service.TransporteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import com.rabbitmq.client.Channel;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmbaladoConsumer {
    private final TransporteService transporteService;
    private final PedidoService pedidoService;
    private final PedidoRepository pedidoRepository;

    @RabbitListener(queues = {"embalado"})
    public void receber(@Payload String mensagem, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        EstadoPedidoMudou pedidoMensagem = objectMapper.readValue(mensagem, EstadoPedidoMudou.class);

        try {
            Pedido pedido = pedidoService.obterPorId(pedidoMensagem.getIdPedido());
            pedido.setStatus(pedidoMensagem.getEstado());
            pedidoRepository.save(pedido);
            log.info("Pedido atualizado: {}", pedido);
            transporteService.processarTransporte(pedido);
            channel.basicAck(deliveryTag, false);
            log.info("Mensagem processada e confirmada: {}", pedidoMensagem);
        } catch (Exception e) {
            channel.basicNack(deliveryTag, false, false);
            log.error("Erro ao processar mensagem: {}, erro: {}", mensagem, e.getMessage());
        }
    }
}
