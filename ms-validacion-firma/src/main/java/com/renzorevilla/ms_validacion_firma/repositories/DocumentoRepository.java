package com.renzorevilla.ms_validacion_firma.repositories;

import com.renzorevilla.ms_validacion_firma.models.Documento;
import com.renzorevilla.ms_validacion_firma.models.Estado;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.time.LocalDateTime;

public interface DocumentoRepository extends MongoRepository<Documento, String> {

    @Query("{ '_id': ?0 }")
    @Update("{ '$set': { 'validacion.estado': ?1, 'validacion.fecha': ?2, 'validacion.firma': ?3 } }")
    void updateValidacionById(String id, Estado estado, LocalDateTime fecha, String firma);
}
