package com.renzorevilla.ms_documentos.services;

import com.renzorevilla.ms_documentos.models.Documento;
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

    public Documento crearDocumento(Documento documento) {

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
        return _repository.save(documento);
    }

    public Documento buscarDocumentoPorId(String idDocumento){
        return _repository.findById(idDocumento)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Documento no encontrado"
                ));
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
