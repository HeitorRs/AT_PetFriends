package br.edu.infnet.pedidos.infra.service;

import br.edu.infnet.pedidos.domain.Pedido;
import br.edu.infnet.pedidos.infra.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private static final Logger LOG = LoggerFactory.getLogger(PedidoService.class);

    private final PedidoRepository pedidoRepository;
    private final AlmoxarifadoService almoxarifadoService;

    public Pedido obterPorId(long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public Pedido fecharPedido(long id) {
        Pedido pedido = pedidoRepository.getReferenceById(id);
        pedido.fecharPedido();
        pedido = pedidoRepository.save(pedido);
        almoxarifadoService.processarAlmoxarifado(pedido);
        return pedido;
    }

}
