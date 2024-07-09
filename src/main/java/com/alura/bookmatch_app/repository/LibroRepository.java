package com.alura.bookmatch_app.repository;

import com.alura.bookmatch_app.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    @Query("SELECT l FROM Libro l LEFT JOIN FETCH l.autores LEFT JOIN FETCH l.idiomas")
    List<Libro> findAllWithAuthorsAndLanguages();
}
