package com.renzorevilla.ms_validacion_firma.models;

import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

public class Item {

    @Field("descripcion")
    private String descripcion;

    @Field("precio")
    private BigDecimal precio;

    @Field("cantidad")
    private BigDecimal cantidad;

    @Field("total")
    private BigDecimal total;

    @Field("igv")
    private BigDecimal igv;


    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getIgv() {
        return igv;
    }

    public void setIgv(BigDecimal igv) {
        this.igv = igv;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
