package com.renzorevilla.ms_documentos.repositories;

import com.renzorevilla.ms_documentos.models.Documento;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentoRepository extends MongoRepository<Documento, String> {

    Documento findTopByOrderByIdDocumentoDesc();

}
