package com.renzorevilla.ms_validacion_firma.messaging;

import com.renzorevilla.ms_validacion_firma.config.RabbitConstants;
import com.renzorevilla.ms_validacion_firma.services.DocumentoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DocumentoListener {

    private DocumentoService _service;

    public DocumentoListener(DocumentoService service){
        this._service = service;
    }

    @RabbitListener(queues = RabbitConstants.COLA_CREADOS)
    public void onMessage(DocumentoCreadoEvent evento) {
        _service.validarDocumento(evento.getIdDocumento());
    }
}
