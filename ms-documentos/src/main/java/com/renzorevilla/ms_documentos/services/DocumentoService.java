package com.renzorevilla.ms_documentos.services;

import com.renzorevilla.ms_documentos.models.Documento;
import com.renzorevilla.ms_documentos.models.DocumentoResponse;
import com.renzorevilla.ms_documentos.models.Estado;
import com.renzorevilla.ms_documentos.models.Validacion;
import com.renzorevilla.ms_documentos.repositories.DocumentoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentoService {

    private DocumentoRepository _repository;

    public DocumentoService(DocumentoRepository repository) {
        this._repository = repository;
    }

    public List<Documento> listarDocumentos() {
        return _repository.findAll();
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
                        "Documento no encontrado"
                ));
    }

    public DocumentoResponse eliminarDocumento(String idDocumento) {
        // Eliminar
        Documento documentoEncontrado = buscarDocumentoPorId(idDocumento);
        documentoEncontrado.getValidacion().setEstado(Estado.ELIMINADO);

        // Respuesta
        Documento documentoEliminado = _repository.save(documentoEncontrado);
        return obtenerRespuestaPostDocumento("Documento eliminado con exito", documentoEliminado);
    }

    // UTILS

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
