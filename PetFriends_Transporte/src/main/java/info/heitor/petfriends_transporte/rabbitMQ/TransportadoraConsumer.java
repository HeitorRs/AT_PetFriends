package info.heitor.petfriends_transporte.rabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.heitor.petfriends_transporte.domain.PedidoMensagem;
import info.heitor.petfriends_transporte.infra.service.EntregaService;
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
public class TransportadoraConsumer {
    private final EntregaService entregaService;

    @RabbitListener(queues = {"transportadora"})
    public void receber(@Payload String mensagem, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PedidoMensagem pedidoMensagem = objectMapper.readValue(mensagem, PedidoMensagem.class);

        try {
            log.info("Processando pedido: {}", pedidoMensagem);
            entregaService.processarPedido(pedidoMensagem);
            channel.basicAck(deliveryTag, false);
            log.info("Mensagem processada e confirmada: {}", pedidoMensagem);
        } catch (Exception e) {
            channel.basicNack(deliveryTag, false, false);
            log.error("Erro ao processar mensagem: {}, erro: {}", mensagem, e.getMessage());
        }
    }
}
