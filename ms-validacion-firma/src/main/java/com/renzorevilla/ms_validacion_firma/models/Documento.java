package com.renzorevilla.ms_validacion_firma.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document(collection = "documento")
public class Documento {

    @Id
    private String idDocumento;

    @Field("uuid")
    public UUID uuid;

    @Field("rucEmisor")
    private String rucEmisor;

    @Field("rucReceptor")
    private String rucReceptor;

    @Field("fecha")
    private LocalDateTime fecha;

    @Field("subtotal")
    private BigDecimal subtotal;

    @Field("igv")
    private BigDecimal igv;

    @Field("total")
    private BigDecimal total;

    @Field("items")
    private List<Item> items;

    @Field("validacion")
    private Validacion validacion;

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

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getIgv() {
        return igv;
    }

    public void setIgv(BigDecimal igv) {
        this.igv = igv;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Validacion getValidacion() {
        return validacion;
    }

    public void setValidacion(Validacion validacion) {
        this.validacion = validacion;
    }

    public String getRucEmisor() {
        return rucEmisor;
    }

    public void setRucEmisor(String rucEmisor) {
        this.rucEmisor = rucEmisor;
    }

    public String getRucReceptor() {
        return rucReceptor;
    }

    public void setRucReceptor(String rucReceptor) {
        this.rucReceptor = rucReceptor;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
