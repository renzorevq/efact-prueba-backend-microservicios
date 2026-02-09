package com.renzorevilla.ms_validacion_firma.config;

import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public TopicExchange documentosExchange() {
        return new TopicExchange(RabbitConstants.EXCHANGE, true, false);
    }

    @Bean
    public Queue documentosCreadosQueue() {
        return new Queue(RabbitConstants.COLA_CREADOS, true);
    }

    @Bean
    public Binding documentosCreadosBinding(Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(RabbitConstants.ROUTING_KEY_DOCUMENTOS_CREADOS);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
