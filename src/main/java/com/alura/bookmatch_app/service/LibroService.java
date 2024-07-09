package com.alura.bookmatch_app.service;

import com.alura.bookmatch_app.model.Libro;
import com.alura.bookmatch_app.repository.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibroService {
    private LibroRepository libroRepository;
    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @Transactional(readOnly = true)
    public List<Libro> listarLibrosGuardados() {
        List<Libro> libros = libroRepository.findAllWithAuthorsAndLanguages();
        // Acceso a autores e idiomas dentro de la transacción
        libros.forEach(libro -> {
            libro.getAutores().size(); // Esto fuerza la carga de los autores
            libro.getIdiomas().size(); // Esto fuerza la carga de los idiomas
        });
        return libros;
    }
}
