package com.alura.bookmatch_app.model;

import com.fasterxml.jackson.annotation.JsonAlias;

//@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") String anioDeNacimiento,
        @JsonAlias("death_year") String anioDeFallecimiento
) {
}
