package com.renzorevilla.ms_documentos.models;

public class ValidacionResponse {

    boolean valido;

    public ValidacionResponse(boolean valido) {
        this.valido = valido;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }
}
