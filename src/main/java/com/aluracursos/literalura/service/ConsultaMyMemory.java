package com.aluracursos.literalura.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * Servicio de integración con la API externa MyMemory (Translated.net).
 * Se encarga de traducir textos del inglés al español.
 * Nota: La API gratuita tiene un límite estricto de 500 caracteres por petición.
 *      Implementa fragmentación (Chunking) para traducir textos largos superando
 *      el límite de 500 caracteres de la versión gratuita.
 */
public class ConsultaMyMemory {

    public static String obtenerTraduccion(String texto) {

        if (texto == null || texto.trim().isEmpty()) {
            return texto;
        }

        // Si es corto, se traduce de una vez
        if (texto.length() <= 490) {
            return realizarPeticion(texto);
        }

        // Si es largo, se aplica Chunking (Fragmentación)
        StringBuilder traduccionCompleta = new StringBuilder();
        int index = 0;

        while (index < texto.length()) {
            // Calcula hasta dónde cortar (máximo 490 caracteres más adelante)
            int endIndex = Math.min(index + 490, texto.length());

            // Si no está en el final del texto, retrocede hasta el último espacio
            // para no cortar una palabra por la mitad
            if (endIndex < texto.length()) {
                int lastSpace = texto.lastIndexOf(" ", endIndex);
                if (lastSpace > index) {
                    endIndex = lastSpace;
                }
            }

            // Extrae el fragmento, lo traduce y lo une al resultado final
            String fragmento = texto.substring(index, endIndex);
            String fragmentoTraducido = realizarPeticion(fragmento);
            traduccionCompleta.append(fragmentoTraducido).append(" ");

            // Avanza el índice para el siguiente ciclo
            index = endIndex;
        }

        return traduccionCompleta.toString().trim();
    }

    private static String realizarPeticion(String texto) {
        // Configuración de la URL base
        // langpair indica traducir del Inglés (en) al Español (es)
        String urlBase = "https://api.mymemory.translated.net/get?q=";
        String langPair = "&langpair=en%7Ces";

        try {
            // 1. CODIFICACIÓN para GET:
            // Conversión de espacios y caracteres especiales a formato URL (ej: "Don Quijote" -> "Don%20Quijote")
            String textoCodificado = URLEncoder.encode(texto, StandardCharsets.UTF_8);
            // Construcción de la URL final
            String urlFinal = urlBase + textoCodificado + langPair;

            // 2. CREACIÓN DEL CLIENTE
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlFinal))
                    .GET()
                    .build()
            ;

            // 3. ENVÍO Y RESPUESTA
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // 4. PARSEO DEL JSON
                // La estructura es: { "responseData": { "translatedText": "Texto Traducido" } }
                JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
                return jsonObject.getAsJsonObject("responseData")
                        .get("translatedText").getAsString();
            } else {
                System.out.println("ERROR EN LA API DE TRADUCCIÓN: " + response.statusCode());
                return texto; // Si la API falla, devuelve el texto original
            }
        } catch (Exception e) {
            System.err.println("ERROR AL CONECTAR CON MYMEMORY: " + e.getMessage());
            return texto; // Si la API falla, devuelve el texto original
        }
    }

}
