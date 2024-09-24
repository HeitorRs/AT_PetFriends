package br.edu.infnet;

import br.edu.infnet.pedidos.domain.PedidoStatus;
import br.edu.infnet.pedidos.eventos.EstadoPedidoMudou;
import br.edu.infnet.pedidos.infra.service.PedidoService;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PetFriendsPedidosApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(PetFriendsPedidosApplication.class);

    @Autowired
    PedidoService service;

    public static void main(String[] args) {
        SpringApplication.run(PetFriendsPedidosApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //EstadoPedidoMudou estado = new EstadoPedidoMudou(1234L, PedidoStatus.FECHADO, new Date());
        //service.enviar(estado);
    }
}
