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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getIgv() {
        return igv;
    }

    public void setIgv(double igv) {
        this.igv = igv;
    }
}
