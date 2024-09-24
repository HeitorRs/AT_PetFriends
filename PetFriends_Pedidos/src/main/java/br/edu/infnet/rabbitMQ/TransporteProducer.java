package br.edu.infnet.rabbitMQ;

import br.edu.infnet.pedidos.domain.ItemPedidoMensagem;
import br.edu.infnet.pedidos.eventos.EstadoPedidoMudou;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransporteProducer {
    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;

    public void enviar(EstadoPedidoMudou mensagem) throws JsonProcessingException {
        amqpTemplate.convertAndSend("transportadora.exc","transportadora.rk", objectMapper.writeValueAsString(mensagem));
    }
}
