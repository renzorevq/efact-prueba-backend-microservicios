package com.renzorevilla.ms_validacion_firma.messaging;

import java.util.UUID;

public class DocumentoCreadoEvent {
    private String idDocumento;
    private UUID uuid;

    public DocumentoCreadoEvent(){}
    public DocumentoCreadoEvent(String idDocumento, UUID uuid){
        this.idDocumento = idDocumento;
        this.uuid = uuid;
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
