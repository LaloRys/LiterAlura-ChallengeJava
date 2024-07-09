package com.alura.bookmatch_app.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        @JsonAlias("count") Integer numeroDeLibros,
        @JsonAlias("results") List<DatosLibro> libros
        ) {
}
