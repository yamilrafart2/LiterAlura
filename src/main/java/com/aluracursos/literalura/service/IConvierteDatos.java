package com.aluracursos.literalura.service;

public interface IConvierteDatos {

    /**
     * Convierte una cadena JSON a un objeto Java de la clase especificada.
     *
     * @param json El String con el formato JSON.
     * @param clase La clase (tipo de dato) a la que queremos convertir.
     * @return Una instancia de la clase especificada con los datos del JSON.
     */
    <T> T obtenerDatos(String json, Class<T> clase);

}
