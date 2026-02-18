package com.aluracursos.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {

    /**
     * Realiza una petición HTTP a la URL especificada para obtener datos.
     *
     * @param url La dirección URL completa de la API o endpoint que se desea consultar.
     * @return Una cadena de texto (String) que contiene la respuesta del servidor (el cuerpo de la respuesta JSON).
     * @throws RuntimeException si ocurre un error de E/S o la ejecución es interrumpida durante la solicitud.
     */
    public String obtenerDatos(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return response.body();
    }

}
