package com.renzorevilla.ms_documentos.services;

import com.renzorevilla.ms_documentos.config.RabbitConstants;
import com.renzorevilla.ms_documentos.messaging.DocumentoCreadoEvent;
import com.renzorevilla.ms_documentos.models.*;
import com.renzorevilla.ms_documentos.repositories.DocumentoRepository;
import jakarta.validation.Validator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentoService {

    private DocumentoRepository _repository;
    private RabbitTemplate _rabbitTemplate;
    private ValidacionService _validacionService;

    public DocumentoService(DocumentoRepository repository, RabbitTemplate rabbitTemplate, ValidacionService validacionService) {
        this._repository = repository;
        this._rabbitTemplate = rabbitTemplate;
        this._validacionService = validacionService;
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
        documento.setFecha(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));

        // Validacion
        Validacion validacionInicial = new Validacion();
        validacionInicial.setFecha(null);
        validacionInicial.setFirma(null);
        validacionInicial.setEstado(Estado.PENDIENTE);

        documento.setValidacion(validacionInicial);

        // documento creado
        Documento documentoCreado = _repository.save(documento);

        // Enviar mensaje
        String idDocuemnto = documentoCreado.getIdDocumento();
        UUID uuid = documentoCreado.getUuid();
        _rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE, RabbitConstants.ROUTING_KEY_DOCUMENTOS_CREADOS,new DocumentoCreadoEvent(idDocuemnto, uuid));

        // retornar respuesta
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

        // Verificar que no halla sido validado
        if(documentoEncontrado.getValidacion().getEstado() == Estado.VALIDO) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El documento ya ha sido validado");
        }

        // Actualizar campos
        try {
            this._repository.patchById(idDocumento, documento);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los datos del documento son incorrectos: " + e.getMessage());
        }

        // Respuesta
        return obtenerRespuestaPostDocumento("El documento ha sido actualizado con exito", documentoEncontrado);
    }

    public ValidacionResponse verificarValidezDocumento(ValidacionRequest request) {
        try {
            return this._validacionService.verificarValidez(request.getDocumento(), request.getFirma());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al intentar validar el documento: " + e.getMessage());
        }
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
