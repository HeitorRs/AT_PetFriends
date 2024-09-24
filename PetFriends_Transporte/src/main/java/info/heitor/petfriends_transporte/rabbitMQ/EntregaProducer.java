package info.heitor.petfriends_transporte.rabbitMQ;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.heitor.petfriends_transporte.domain.PedidoMensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntregaProducer {
    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;

    public void enviar(PedidoMensagem mensagem) throws JsonProcessingException {
        amqpTemplate.convertAndSend("entrega.exc","entrega.rk", objectMapper.writeValueAsString(mensagem));
    }
}