package com.renzorevilla.ms_validacion_firma.services;

import com.renzorevilla.ms_validacion_firma.models.Documento;
import com.renzorevilla.ms_validacion_firma.models.Estado;
import com.renzorevilla.ms_validacion_firma.repositories.DocumentoRepository;
import org.springframework.stereotype.Service;

@Service
public class DocumentoService {

    private DocumentoRepository _repository;
    private FirmaService _firmaService;
    private ValidacionService _validacionService;

    public DocumentoService(DocumentoRepository repository, FirmaService firmaService, ValidacionService validacionService) {
        this._repository = repository;
        this._firmaService = firmaService;
        this._validacionService = validacionService;
    }

    public void validarDocumento(String idDocumento) {
        try {
            Documento documento = _repository.findById(idDocumento)
                    .orElseThrow(() -> new RuntimeException("El documento no exitste"));

            if(documento.getValidacion().getEstado() == Estado.ELIMINADO)
                throw new RuntimeException("El documento esta eliminado");

            boolean esValido = _validacionService.validarValores(documento);

            Documento documentoValidado = _validacionService.agregarValidacionDocumento(documento, esValido);

            if (esValido)
                documentoValidado = _firmaService.firmarDocumento(documento);

            _repository.updateValidacionById(
                    idDocumento,
                    documentoValidado.getValidacion().getEstado(),
                    documentoValidado.getValidacion().getFecha(),
                    documentoValidado.getValidacion().getFirma()
            );
        } catch (RuntimeException e) {
            System.out.println("Error al procesar la firma en " + idDocumento + ": " + e.getMessage());
        }
    }

}
