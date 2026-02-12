package com.renzorevilla.ms_documentos.repositories.impl;

import com.renzorevilla.ms_documentos.models.Documento;
import com.renzorevilla.ms_documentos.models.Item;
import com.renzorevilla.ms_documentos.repositories.DocumentoRespositoryCustom;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class DocumentoRepositoryImpl implements DocumentoRespositoryCustom {

    private MongoTemplate _mongoTemplate;

    public DocumentoRepositoryImpl(MongoTemplate mongoTemplate) {
        this._mongoTemplate = mongoTemplate;
    }

    @Override
    public void patchById(String idDocumento, Documento documento) {
        Query q = new Query(Criteria.where("idDocumento").is(idDocumento));
        Update u = new Update();

        if (documento.getRucEmisor() != null)   u.set("rucEmisor", documento.getRucEmisor());
        if (documento.getRucReceptor() != null) u.set("rucReceptor", documento.getRucReceptor());
        if (documento.getSubtotal() != null)    u.set("subtotal", documento.getSubtotal());
        if (documento.getIgv() != null)         u.set("igv", documento.getIgv());
        if (documento.getTotal() != null)       u.set("total", documento.getTotal());

        if (documento.getItems() != null) {

            for (Item item : documento.getItems()) {
                if (item.getDescripcion() == null) {
                    throw new IllegalArgumentException("La descripcion en item es obligatoria");
                }
                if (item.getCantidad() == null) {
                    throw new IllegalArgumentException("La cantidad en item es obligatoria");
                }
                if (item.getPrecio() == null) {
                    throw new IllegalArgumentException("El precio en item es obligatoria");
                }
                if (item.getTotal() == null) {
                    throw new IllegalArgumentException("El total en item es obligatoria");
                }
                if (item.getIgv() == null) {
                    throw new IllegalArgumentException("El igv en item es obligatoria");
                }
            }
            u.set("items", documento.getItems());
        }

        if (u.getUpdateObject().isEmpty()) return;

        _mongoTemplate.updateFirst(q, u, Documento.class);
    }
}
