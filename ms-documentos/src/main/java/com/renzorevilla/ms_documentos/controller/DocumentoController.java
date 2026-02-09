package com.renzorevilla.ms_documentos.controller;

import com.renzorevilla.ms_documentos.models.Documento;
import com.renzorevilla.ms_documentos.models.DocumentoResponse;
import com.renzorevilla.ms_documentos.services.DocumentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/documents")
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
    public DocumentoResponse crearDocumento(@Valid @RequestBody Documento documento) {
        return this._service.crearDocumento(documento);
    }

    @GetMapping("/{idDocumento}")
    @ResponseStatus(HttpStatus.OK)
    public Documento buscarDocuemnto(@PathVariable String idDocumento) {
        return _service.buscarDocumentoPorId(idDocumento);
    }

    @DeleteMapping("/{idDocumento}")
    @ResponseStatus(HttpStatus.OK)
    public DocumentoResponse eliminarDocumento(@PathVariable String idDocumento) {
        return _service.eliminarDocumento(idDocumento);
    }

    @PutMapping("/{idDocumento}")
    @ResponseStatus(HttpStatus.OK)
    public DocumentoResponse actualizarDocumento(@PathVariable String idDocumento, @RequestBody Documento documento) {
        return this._service.actualizarDocumento(idDocumento, documento);
    }
}
