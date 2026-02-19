package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> listaAutores,
        @JsonAlias("summaries") List<String> resumen,
        @JsonAlias("languages") List<String> listaIdiomas,
        @JsonAlias("download_count") Double totalDescargas
) {
}
