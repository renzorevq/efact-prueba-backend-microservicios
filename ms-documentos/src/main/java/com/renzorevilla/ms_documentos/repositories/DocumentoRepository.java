package com.renzorevilla.ms_documentos.repositories;

import com.renzorevilla.ms_documentos.models.Documento;
import com.renzorevilla.ms_documentos.models.Estado;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DocumentoRepository extends MongoRepository<Documento, String> {

    Documento findTopByOrderByIdDocumentoDesc();

    List<Documento> findByValidacionEstadoNot(Estado estado);
}
