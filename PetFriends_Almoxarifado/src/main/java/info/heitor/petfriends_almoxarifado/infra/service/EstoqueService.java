package info.heitor.petfriends_almoxarifado.infra.service;

import info.heitor.petfriends_almoxarifado.domain.Estoque;
import info.heitor.petfriends_almoxarifado.domain.PedidoMensagem;
import info.heitor.petfriends_almoxarifado.domain.PedidoStatus;
import info.heitor.petfriends_almoxarifado.infra.repository.EstoqueRepository;
import info.heitor.petfriends_almoxarifado.rabbitMQ.EmbaladoProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final EmbaladoProducer embaladoProducer;

    public void processarPedido(PedidoMensagem mensagem) {

        List<PedidoMensagem.ItemPedido> itens = mensagem.getItens();

        for (PedidoMensagem.ItemPedido item : itens) {
            Long produtoId = item.getProductId();
            int quantidade = item.getQuantity();

            Estoque estoque = estoqueRepository.findByProductId(produtoId);
            if (estoque == null || estoque.getQuantity() < quantidade) {
                log.error("Estoque insuficiente para o produto ID: {}", produtoId);
                throw new RuntimeException();
            }

            estoque.removerQuantidade(quantidade);
            estoqueRepository.save(estoque);
            log.info("Quantidade de produto ID {} reduzida para {}", produtoId, estoque.getQuantity());
        }
        processarPedidoEmbalado(mensagem);
    }

    public void processarPedidoEmbalado(PedidoMensagem mensagem){
        try {
            mensagem.setEstado(PedidoStatus.EMBALADO);
            log.info("Pedido embalado {}",mensagem);
            embaladoProducer.enviar(mensagem);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
