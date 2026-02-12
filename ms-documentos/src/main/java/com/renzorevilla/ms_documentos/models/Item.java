package com.renzorevilla.ms_documentos.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Item {

    @NotBlank(message = "El campo es obligatorio")
    private String descripcion;

    @NotNull(message = "El campo es obligatorio")
    private Double precio;

    @NotNull(message = "El campo es obligatorio")
    private Double cantidad;

    @NotNull(message = "El campo es obligatorio")
    private Double total;

    @NotNull(message = "El campo es obligatorio")
    private Double igv;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getIgv() {
        return igv;
    }

    public void setIgv(Double igv) {
        this.igv = igv;
    }
}
