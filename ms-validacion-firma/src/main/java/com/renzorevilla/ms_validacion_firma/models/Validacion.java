package com.renzorevilla.ms_validacion_firma.models;

import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

public class Validacion {

    @Field("fecha")
    private LocalDateTime fecha;

    @Field("firma")
    private String firma;

    @Field("estado")
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
