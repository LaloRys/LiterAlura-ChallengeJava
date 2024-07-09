package com.alura.bookmatch_app.principal;

import com.alura.bookmatch_app.model.Autor;
import com.alura.bookmatch_app.model.DatosLibro;
import com.alura.bookmatch_app.model.DatosLibros;
import com.alura.bookmatch_app.model.Libro;
import com.alura.bookmatch_app.repository.LibroRepository;
import com.alura.bookmatch_app.service.ConsumoAPI;
import com.alura.bookmatch_app.service.ConvierteDatos;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor =  new ConvierteDatos();
    private Scanner sc = new Scanner(System.in);
    private final String URL_BASE = "https://gutendex.com/books/?";
    private final String SEARCH = "search=";
    private String titulo;
    private LibroRepository libroRepository;

    public Principal(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }


    public void iniciarPrograma() {
        var opcion = -1;
        while (opcion != 0) {
            System.out.println("""
                    --------------------------
                    Elige una opcion: 
                    1. Buscar libro por titulo
                    2. Listar libros guardados
                                        
                    0. Salir
                    """);
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1 -> buscarLibroApi();
                case 2 -> listarLibrosGuardados();

                case 0 -> System.out.println("Cerrando la aplicacion... Adios...");
                default -> System.out.println("Opcion no valida");
            }
        }

    }

    private void listarLibrosGuardados() {
        var libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros guardados");
        } else {
            System.out.println("Libros guardados: ");
            libros.forEach(l -> {
                System.out.printf("""
                    ----- LIBRO -----
                    Titulo: %s
                    Autor(es): %s
                    Numero de descargas: %.0f
                    -----------------
                    """, l.getTitulo(), l.getAutores().get(0) ,l.getNumeroDeDescargas());
            });
        }
    }

    private DatosLibros getLibro() {
        System.out.println("Escribe el nombre del libro que deseas buscar: ");
        titulo = sc.nextLine();
        var json = consumoApi.obtenerLibros(URL_BASE + SEARCH + titulo.replace(" ", "%20"));
//        System.out.println("Consulta api ->" + titulo+ " -- json: " + json);
        DatosLibros datos = conversor.obtenerDatos(json, DatosLibros.class);
        return datos;
    }

    private void buscarLibroApi() {
        var datos = getLibro();
        System.out.println("datos = " + datos);
        if (datos.numeroDeLibros() == 0) {
            System.out.println("No se encontro el libro");
        } else {
            System.out.println("Se encontro " + datos.numeroDeLibros() + " libros");
            datos.libros().forEach(System.out::println);
            System.out.println("Primer libro = " + datos.libros().get(0));
            var busquedaLibro = datos.libros().stream()
                    .filter(libro -> libro.titulo().toLowerCase().equals(titulo.toLowerCase()))
                    .collect(Collectors.toList());
            if (busquedaLibro.isEmpty()) {
                System.out.println("No se encontro el libro con el título exacto: " + titulo);
                System.out.println("Primer libro con titulo similar: ");
                var libroEncontrado = datos.libros().get(0);
                imprimirLibro(libroEncontrado);
                crearYGuardarLibro(libroEncontrado);

            } else {
                var libroEncontrado = busquedaLibro.get(0);
                System.out.println("Se encontró el libro con el título exacto: " + libroEncontrado);
                imprimirLibro(libroEncontrado);
                crearYGuardarLibro(libroEncontrado);
            }
        }
    }

    private void crearYGuardarLibro(DatosLibro datosLibro) {
        try {
            Libro libro = new Libro(datosLibro);
            // Mapear autores desde DatosAutor a Autor y establecerlos en el libro
            List<Autor> autores = datosLibro.autores().stream()
                    .map(datosAutor -> {
                        Integer añoNacimiento = Integer.parseInt(datosAutor.anioDeNacimiento());
                        Integer añoFallecimiento = Integer.parseInt(datosAutor.anioDeFallecimiento());
                        Autor autor = new Autor(datosAutor.nombre(), añoNacimiento, añoFallecimiento);
                        autor.setLibro(libro); // Establecer la relación bidireccional si es necesario
                        return autor;
                    })
                    .toList();
            libro.setAutores(autores);
            libroRepository.save(libro);
            System.out.println("Libro guardado correctamente");
        } catch (Exception e) {
            System.out.println("Error al guardar el libro: " + e.getMessage());
        }
    }

    private void imprimirLibro(DatosLibro libro) {
        String autor = libro.autores().isEmpty() ? "Desconocido" : libro.autores().get(0).nombre();
        System.out.printf("""
                    ----- LIBRO -----
                    Titulo: %s
                    Autor: %s
                    Idioma: %s
                    Numero de descargas: %.0f
                    -----------------
                    """, libro.titulo(), autor, libro.idiomas().get(0), libro.numeroDeDescargas());
    }
}
