package br.edu.infnet.rabbitMQ;

import br.edu.infnet.pedidos.eventos.EstadoPedidoMudou;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlmoxafrifadoProducer {
    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;

    public void enviar(EstadoPedidoMudou estado) throws JsonProcessingException {
        amqpTemplate.convertAndSend("almoxarifado.exc","almoxarifado.rk", objectMapper.writeValueAsString(estado));
    }
}
