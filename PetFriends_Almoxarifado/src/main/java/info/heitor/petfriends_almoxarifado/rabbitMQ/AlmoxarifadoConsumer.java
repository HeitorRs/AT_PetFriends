package info.heitor.petfriends_almoxarifado.rabbitMQ;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.heitor.petfriends_almoxarifado.domain.PedidoMensagem;
import info.heitor.petfriends_almoxarifado.infra.service.EstoqueService;
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
public class AlmoxarifadoConsumer {
    private final EstoqueService estoqueService;

    @RabbitListener(queues = {"almoxarifado"})
    public void receber(@Payload String mensagem, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PedidoMensagem pedidoMensagem = objectMapper.readValue(mensagem, PedidoMensagem.class);

        try {
            estoqueService.processarPedido(pedidoMensagem);
            channel.basicAck(deliveryTag, false);
            log.info("Mensagem processada e confirmada: {}", pedidoMensagem);
        } catch (Exception e) {
            channel.basicNack(deliveryTag, false, false);
            log.error("Erro ao processar mensagem: {}, erro: {}", mensagem, e.getMessage());
        }
    }
}
