package com.renzorevilla.ms_validacion_firma.services;

import com.renzorevilla.ms_validacion_firma.models.Documento;
import com.renzorevilla.ms_validacion_firma.models.Estado;
import com.renzorevilla.ms_validacion_firma.models.Item;
import com.renzorevilla.ms_validacion_firma.models.Tasa;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
        documento.getValidacion().setFecha(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        return documento;
    }

    private boolean validarItems(Documento documento) {

        return documento
                .getItems()
                .stream()
                .allMatch(item -> {

                    BigDecimal subtotal = item.getPrecio().multiply(item.getCantidad());
                    BigDecimal igv = subtotal.multiply(Tasa.IGV).setScale(2, RoundingMode.HALF_UP);

                    return item.getTotal().compareTo(subtotal.add(igv)) == 0;
                });
    }

    private boolean validarTotales(Documento documento) {

        BigDecimal subTotal = documento
                .getItems()
                .stream()
                .map(i -> i.getPrecio().multiply(i.getCantidad()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if(documento.getSubtotal().compareTo(subTotal) != 0)
            return false;

        BigDecimal igv = documento
                .getItems()
                .stream()
                .map(Item::getIgv)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if(documento.getIgv().compareTo(igv) != 0)
            return false;

        BigDecimal total = documento.getSubtotal().add(documento.getIgv());

        return documento.getTotal().compareTo(total) == 0;
    }

}
