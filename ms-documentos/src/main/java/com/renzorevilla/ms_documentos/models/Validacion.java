package com.renzorevilla.ms_documentos.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

public class Validacion {

    private LocalDateTime fecha;
    private String firma;
    private Estado estado;

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
