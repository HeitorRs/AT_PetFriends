package br.edu.infnet.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    //Fila Almoxarifado
    @Bean
    public Queue almoxarifadoQueue() {
        return new Queue("almoxarifado", true);
    }

    //Exchange Almoxarifado
    @Bean
    public TopicExchange almoxarifadoExchange() {
        return new TopicExchange("almoxarifado.exc");
    }

    //Bindings Almoxarifado
    @Bean
    public Binding bindingAlmoxarifado(Queue almoxarifadoQueue, TopicExchange almoxarifadoExchange) {
        return BindingBuilder.bind(almoxarifadoQueue).to(almoxarifadoExchange).with("almoxarifado.rk");
    }

    //Fila Embalado
    @Bean
    public Queue embaladoQueue() {
        return new Queue("embalado", true);
    }

    //Exchange Embalado
    @Bean
    public TopicExchange embaladoExchange() {
        return new TopicExchange("embalado.exc");
    }

    //Bindings Embalado
    @Bean
    public Binding bindingEmbalado(Queue embaladoQueue, TopicExchange embaladoExchange) {
        return BindingBuilder.bind(embaladoQueue).to(embaladoExchange).with("embalado.rk");
    }

    //Fila Transportadora
    @Bean
    public Queue transportadoraQueue() {
        return new Queue("transportadora", true);
    }

    //Exchange Transportadora
    @Bean
    public TopicExchange transportadoraExchange() {
        return new TopicExchange("transportadora.exc");
    }

    //Bindings Transportadora
    @Bean
    public Binding bindingTransportadora(Queue transportadoraQueue, TopicExchange transportadoraExchange) {
        return BindingBuilder.bind(transportadoraQueue).to(transportadoraExchange).with("transportadora.rk");
    }

    //Fila Entrega
    @Bean
    public Queue entregaQueue() {
        return new Queue("entrega", true);
    }

    //Exchange Entrega
    @Bean
    public TopicExchange entregaExchange() {
        return new TopicExchange("entrega.exc");
    }

    //Bindings Entrega
    @Bean
    public Binding bindingEntrega(Queue entregaQueue, TopicExchange entregaExchange) {
        return BindingBuilder.bind(entregaQueue).to(entregaExchange).with("entrega.rk");
    }
}
