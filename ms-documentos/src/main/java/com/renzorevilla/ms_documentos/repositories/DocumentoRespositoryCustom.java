package com.renzorevilla.ms_documentos.repositories;

import com.renzorevilla.ms_documentos.models.Documento;

public interface DocumentoRespositoryCustom {
    void patchById(String idDocumento, Documento documento);
}
