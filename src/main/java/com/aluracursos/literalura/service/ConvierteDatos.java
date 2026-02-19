package com.aluracursos.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implementación del servicio de conversión de datos.
 * Utiliza la librería Jackson (ObjectMapper) para deserializar el JSON crudo
 * obtenido de la API y convertirlo en Records/DTOs de Java.
 */
public class ConvierteDatos implements IConvierteDatos {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
