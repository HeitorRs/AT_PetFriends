package info.heitor.petfriends_transporte.infra.service;

import info.heitor.petfriends_transporte.domain.Endereco;
import info.heitor.petfriends_transporte.domain.Entrega;
import info.heitor.petfriends_transporte.domain.PedidoMensagem;
import info.heitor.petfriends_transporte.domain.PedidoStatus;
import info.heitor.petfriends_transporte.infra.repository.EntregaRepository;
import info.heitor.petfriends_transporte.rabbitMQ.EntregaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class EntregaService {

    private final EntregaRepository entregaRepository;
    private final EntregaProducer entregueProducer;
    private final Random random = new Random();

    public void processarPedido(PedidoMensagem mensagem) {
        log.info("Iniciando o processamento do pedido: {}", mensagem);
        Entrega entrega = new Entrega();
        entrega.setPedidoId(mensagem.getIdPedido());
        Long motoristaId = (long) (random.nextInt(100) + 1);
        entrega.setMotoristaId(motoristaId);
        Endereco enderecoAleatorio = gerarEnderecoAleatorio();
        entrega.setEndereco(enderecoAleatorio);
        entregaRepository.save(entrega);
        log.info("Pedido {} enviado com sucesso!",mensagem);

        try {
            Thread.sleep(5000);
            log.info("Pedido {} entregue com sucesso!",mensagem);
            processarPedidoEntregue(mensagem);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void processarPedidoEntregue(PedidoMensagem mensagem){
        try {
            mensagem.setEstado(PedidoStatus.ENTREGUE);
            entregueProducer.enviar(mensagem);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Endereco gerarEnderecoAleatorio() {
        Endereco endereco = new Endereco();
        String[] ruas = {"Rua A", "Rua B", "Rua C", "Rua D", "Rua E"};
        String[] cidades = {"São Paulo", "Rio de Janeiro", "Belo Horizonte", "Curitiba", "Porto Alegre"};
        String[] estados = {"SP", "RJ", "MG", "PR", "RS"};
        String cep = String.format("%05d-%03d", random.nextInt(100000), random.nextInt(1000));

        endereco.setRua(ruas[random.nextInt(ruas.length)]);
        endereco.setNumero(String.valueOf(random.nextInt(1000) + 1)); // Gera um número entre 1 e 1000
        endereco.setCidade(cidades[random.nextInt(cidades.length)]);
        endereco.setEstado(estados[random.nextInt(estados.length)]);
        endereco.setCep(cep);

        return endereco;
    }
}
