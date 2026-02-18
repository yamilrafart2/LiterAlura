package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.DatosResultados;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

/**
 * Clase principal que maneja la interacción con el usuario y el flujo de la aplicación.
 */
public class Principal {

    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();

    public void mostrarMenu() {

        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println("\n============================================================");
        System.out.println(json);

        var datosResultados = conversor.obtenerDatos(json, DatosResultados.class);
        System.out.println( "\n" + datosResultados);
        System.out.println("============================================================");

    }
}
