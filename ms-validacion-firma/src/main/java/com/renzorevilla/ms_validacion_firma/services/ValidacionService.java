package com.renzorevilla.ms_validacion_firma.services;

import com.renzorevilla.ms_validacion_firma.models.Documento;
import com.renzorevilla.ms_validacion_firma.models.Estado;
import com.renzorevilla.ms_validacion_firma.models.Item;
import com.renzorevilla.ms_validacion_firma.models.Tasa;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class ValidacionService {

    public boolean validarValores(Documento documento) {

        // Validar Items
        boolean esItemsValido = validarItems(documento);

        if(!esItemsValido)
            return false;

        // Validar Totales
        return validarTotales(documento);
    }

    public Documento agregarValidacionDocumento(Documento documento, boolean esValido) {
        documento.getValidacion().setEstado(esValido ? Estado.VALIDO : Estado.INVALIDO);
        documento.getValidacion().setFecha(LocalDateTime.now());
        return documento;
    }

    private boolean validarItems(Documento documento) {

        return documento
                .getItems()
                .stream()
                .allMatch(item -> {

                    BigDecimal total = item.getPrecio().multiply(item.getCantidad());
                    BigDecimal igv = item.getTotal().multiply(Tasa.IGV).setScale(2, RoundingMode.HALF_UP);

                    return item.getTotal().compareTo(total) == 0 &&
                            item.getIgv().compareTo(igv) == 0;

                });
    }

    private boolean validarTotales(Documento documento) {

        BigDecimal subTotal = documento
                .getItems()
                .stream()
                .map(Item::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if(documento.getSubTotal().compareTo(subTotal) != 0)
            return false;

        BigDecimal igv = documento
                .getItems()
                .stream()
                .map(Item::getIgv)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if(documento.getIgv().compareTo(igv) != 0)
            return false;

        BigDecimal total = documento.getSubTotal().add(documento.getIgv());

        return documento.getTotal().compareTo(total) == 0;
    }

}
