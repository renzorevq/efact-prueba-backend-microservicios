package com.renzorevilla.ms_documentos.models;

public class ValidacionRequest {

    private Documento documento;
    private String firma;

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }
}
