package com.renzorevilla.ms_documentos.config;

import org.bson.UuidRepresentation;
import org.springframework.boot.mongodb.autoconfigure.MongoClientSettingsBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

    @Bean
    public MongoClientSettingsBuilderCustomizer uuidStandard() {
        return builder -> builder.uuidRepresentation(UuidRepresentation.STANDARD);
    }

}
