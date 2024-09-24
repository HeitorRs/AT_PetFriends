package br.edu.infnet.controllers;

import br.edu.infnet.pedidos.domain.Pedido;
import br.edu.infnet.pedidos.infra.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {
    
    @Autowired
    private PedidoService service;
    
    @GetMapping("/{id}")
    public Pedido obterPorId(@PathVariable(value = "id") long id){ 
        return service.obterPorId(id);
    }
    
    @PatchMapping("/fechar-pedido/{id}")
    public Pedido fecharPedido(@PathVariable(value = "id") long id){ 
        return service.fecharPedido(id);
    }
}
