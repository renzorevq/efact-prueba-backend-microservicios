package com.renzorevilla.ms_validacion_firma.messaging;

import com.renzorevilla.ms_validacion_firma.config.RabbitConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DocumentoListener {

    @RabbitListener(queues = RabbitConstants.COLA_CREADOS)
    public void onMessage(DocumentoCreadoEvent evento) {
        System.out.println("idDocumento=" + evento.getIdDocumento());
        System.out.println("uuid=" + evento.getUuid());
    }

}
