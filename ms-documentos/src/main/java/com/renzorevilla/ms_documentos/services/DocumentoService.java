package com.renzorevilla.ms_documentos.services;

import com.renzorevilla.ms_documentos.models.Documento;
import com.renzorevilla.ms_documentos.models.DocumentoResponse;
import com.renzorevilla.ms_documentos.models.Estado;
import com.renzorevilla.ms_documentos.models.Validacion;
import com.renzorevilla.ms_documentos.repositories.DocumentoRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.print.Doc;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class DocumentoService {

    private DocumentoRepository _repository;
    private Validator _validator;

    public DocumentoService(DocumentoRepository repository, Validator validator) {
        this._repository = repository;
        this._validator = validator;
    }

    public List<Documento> listarDocumentos() {
        return _repository.findByValidacionEstadoNot(Estado.ELIMINADO);
    }

    public DocumentoResponse crearDocumento(Documento documento) {

        // Id de ultimo Documento
        Documento ultimoDocumento = buscarUltimoDocumento();

        // Correlativo de id
        String idDocumento = obtenerIdDocumentoSiguiente(ultimoDocumento);

        // Documento
        documento.setIdDocumento(idDocumento);
        documento.setUuid(UUID.randomUUID());
        documento.setFecha(LocalDateTime.now());

        // Validacion
        Validacion validacionInicial = new Validacion();
        validacionInicial.setFecha(null);
        validacionInicial.setFirma(null);
        validacionInicial.setEstado(Estado.PENDIENTE);

        documento.setValidacion(validacionInicial);

        // return
        Documento documentoCreado = _repository.save(documento);
        return obtenerRespuestaPostDocumento("Documento creado con exito", documentoCreado);
    }

    public Documento buscarDocumentoPorId(String idDocumento){
        return _repository.findById(idDocumento)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "El documento no existe"
                ));
    }

    public DocumentoResponse eliminarDocumento(String idDocumento) {

        // Buscar el docuemnto
        Documento documentoEncontrado = buscarDocumentoPorId(idDocumento);

        // Verificar que aun no este eliminado
        if(documentoEncontrado.getValidacion().getEstado() == Estado.ELIMINADO) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El documento ya ha sido eliminado");
        }

        // Eliminar
        documentoEncontrado.getValidacion().setEstado(Estado.ELIMINADO);
        _repository.save(documentoEncontrado);

        // Respuesta
        return obtenerRespuestaPostDocumento("Documento eliminado con exito", documentoEncontrado);
    }

    public DocumentoResponse actualizarDocumento(String idDocumento, Documento documento){
        // Buscar el docuemnto
        Documento documentoEncontrado = buscarDocumentoPorId(idDocumento);

        // Verificar que el documento no este eliminado
        if(documentoEncontrado.getValidacion().getEstado() == Estado.ELIMINADO) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El documento esta eliminado. No se pudo actualizar.");
        }

        // Actualizar campos
        Documento documentoValido = validarCamposDocumento(documentoEncontrado, documento);
        this._repository.save(documentoValido);

        // Respuesta
        return obtenerRespuestaPostDocumento("El documento ha sido actualizado con exito", documentoEncontrado);
    }

    // UTILS

    private Documento validarCamposDocumento(Documento documentoEncontrado, Documento documento){

        if(validarPropiedadEnDocumento(documento.getRucEmisor(), "rucEmisor"))
            documentoEncontrado.setRucEmisor(documento.getRucEmisor());

        if(validarPropiedadEnDocumento(documento.getRucReceptor(), "rucReceptor"))
            documentoEncontrado.setRucReceptor(documento.getRucReceptor());

        if(validarPropiedadEnDocumento(documento.getSubtotal(), "subtotal"))
            documentoEncontrado.setSubtotal(documento.getSubtotal());

        if(validarPropiedadEnDocumento(documento.getIgv(), "igv"))
            documentoEncontrado.setIgv(documento.getIgv());

        if(validarPropiedadEnDocumento(documento.getTotal(), "total"))
            documentoEncontrado.setTotal(documento.getTotal());

        if(validarPropiedadEnDocumento(documento.getItems(), "items"))
            documentoEncontrado.setItems(documento.getItems());

        return documentoEncontrado;
    }

    private boolean validarPropiedadEnDocumento(Object valor, String propiedad){
        if (valor != null) {
            Set<ConstraintViolation<Documento>> error = _validator.validateValue(Documento.class, propiedad, valor);
            if(!error.isEmpty()){
                String mensaje = error.iterator().next().getMessage();
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, mensaje);
            }
        }

        return valor != null;
    }

    private DocumentoResponse obtenerRespuestaPostDocumento(String mensaje, Documento documento) {
        DocumentoResponse response = new DocumentoResponse();
        response.setMensaje(mensaje);
        response.setIdDocumento(documento.getIdDocumento());
        response.setUuid(documento.getUuid());
        return response;
    }

    private Documento buscarUltimoDocumento(){
        return _repository.findTopByOrderByIdDocumentoDesc();
    }

    private String obtenerIdDocumentoSiguiente(Documento ultimoDocumento){

        // Obtener correlativo
        int correlativo = 1;

        if(ultimoDocumento != null) {
            int ultimoCorrelativo = Integer.parseInt(ultimoDocumento.getIdDocumento().split("-")[1]);
            correlativo = ultimoCorrelativo + 1;
        }

        // Generar id
        String idDocumento = "FACT-" + String.format("%09d", correlativo);

        // retornar id
        return idDocumento;
    }
}
