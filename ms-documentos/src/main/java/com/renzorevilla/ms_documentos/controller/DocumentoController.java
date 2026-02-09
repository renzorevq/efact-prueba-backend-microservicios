package com.renzorevilla.ms_documentos.controller;

import com.renzorevilla.ms_documentos.models.Documento;
import com.renzorevilla.ms_documentos.services.DocumentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/documento")
public class DocumentoController {

    private DocumentoService _service;

    public DocumentoController(DocumentoService service) {
        this._service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Documento> listarDocumentos(){
        return this._service.listarDocumentos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> crearDocumento(@Valid @RequestBody Documento documento) {
        Documento documentoNuevo = this._service.crearDocumento(documento);

        return Map.of(
                "mensaje", "Documento " + documentoNuevo.getIdDocumento() + " creado correctamente",
                "idDocumento", documentoNuevo.getIdDocumento(),
                "uuid", documentoNuevo.getUuid()
        );
    }
}
