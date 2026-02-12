package com.renzorevilla.ms_documentos.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "documento")
public class Documento {

    @Id
    private String idDocumento;

    private UUID uuid;

    @NotBlank(message = "El campo es obligatorio")
    @Pattern(
        regexp = "^\\d{11}$",
        message = "El RUC emisor debe tener 11 digitos"
    )
    private String rucEmisor;

    @NotBlank(message = "El campo es obligatorio")
    @Pattern(
            regexp = "^\\d{11}$",
            message = "El RUC receptor debe tener 11 digitos"
    )
    private String rucReceptor;

//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime fecha;

    @NotNull(message = "El campo es obligatorio")
    private Double subtotal;

    @NotNull(message = "El campo es obligatorio")
    private Double igv;

    @NotNull(message = "El campo es obligatorio")
    private Double total;

    @NotEmpty(message = "Debe existir al menos 1 item")
    @Valid
    private List<Item> items;

    private Validacion validacion;

    public Validacion getValidacion() {
        return validacion;
    }

    public void setValidacion(Validacion validacion) {
        this.validacion = validacion;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
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

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getRucReceptor() {
        return rucReceptor;
    }

    public void setRucReceptor(String rucReceptor) {
        this.rucReceptor = rucReceptor;
    }

    public String getRucEmisor() {
        return rucEmisor;
    }

    public void setRucEmisor(String rucEmisor) {
        this.rucEmisor = rucEmisor;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    @Override
    public String toString() {
        return "Documento{" +
                "idDocumento='" + idDocumento + '\'' +
                ", uuid=" + uuid +
                ", rucEmisor='" + rucEmisor + '\'' +
                ", rucReceptor='" + rucReceptor + '\'' +
                ", fecha=" + fecha +
                ", subtotal=" + subtotal +
                ", igv=" + igv +
                ", total=" + total +
                ", items=" + items +
                ", validacion=" + validacion +
                '}';
    }
}
