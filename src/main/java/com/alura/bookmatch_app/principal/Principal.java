package com.alura.bookmatch_app.principal;

import com.alura.bookmatch_app.model.*;
import com.alura.bookmatch_app.repository.RepositorioAutor;
import com.alura.bookmatch_app.service.ConsumoAPI;
import com.alura.bookmatch_app.service.ConvierteDatos;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private ConsumoAPI consumoApi = new ConsumoAPI(); //conexionURL.consultar
    private ConvierteDatos conversor = new ConvierteDatos(); //conversor.convertir
    private Scanner sc = new Scanner(System.in); //teclado
    private final String URL_BASE = "https://gutendex.com/books/?";
    private final String SEARCH = "search=";
    private RepositorioAutor repositorioAutor;

    public Principal(RepositorioAutor repositorioAutor) {
        this.repositorioAutor = repositorioAutor;
    }


    public void iniciarPrograma() throws IOException, InterruptedException {
        var opcion = -1;
        while (opcion != 0) {
            System.out.println("""
                    --------------------------
                    Elige una opcion: 
                                        
                    1. Buscar libro por titulo
                    2. Listar libros guardados
                    3. Listar autores guardados
                    4. Listar autores vivos en un determinado año
                    5. Listar libros por idioma
                    6. Listar Libros por autor
                    7. Listar 10 más descargados registrados
                                        
                    0. Salir
                    """);
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> mostrarLibrosRegistrados();
                case 3 -> mostrarAutores();
                case 4 -> mostrarLiveYearCheck();
                case 5 -> mostrarLibrosPorIdioma();
                case 6 -> mostrarLibrosPorAutor();
                case 7 -> mostrar10MasDescargados();

                case 0 -> System.out.println("Cerrando la aplicacion... Adios...");
                default -> System.out.println("Opcion no valida");
            }
        }

    }

    private void mostrar10MasDescargados() {
        List<Titulo> titulosTemp = repositorioAutor.masDescargados();
        titulosTemp.forEach(System.out::println);
    }

    private void mostrarLibrosPorAutor() {
        System.out.println("Escribe el nombre del autor");
        String nombreAutor = sc.nextLine();
        List<Titulo> titulosTemp = repositorioAutor.tituloPorAutor(nombreAutor);
        if (titulosTemp.isEmpty()) {
            System.out.println("Sin resultados");
        }
        titulosTemp.forEach(System.out::println);
    }

    private void mostrarLibrosPorIdioma() {
        System.out.println("""
                es: Español
                pt: Portugués
                fr: Francés
                en: Inglés
                """);
        System.out.println("Ingrese el idioma para buscar los libros:");
        String entrada = sc.nextLine();
        List<Titulo> titulosTemp = repositorioAutor.listaTitulosPorIdioma(entrada);
        if (titulosTemp.isEmpty()) {
            System.out.println("Sin resultados");
        }
        titulosTemp.forEach(System.out::println);
    }

    private void mostrarLiveYearCheck() {
        System.out.println("Ingrese el año vivo de autor(es) que desea buscar");
        Integer year = sc.nextInt();
        sc.nextLine();

        List<Autor> autorsTemp = repositorioAutor.liveYearCheck(year);
        if (autorsTemp.isEmpty()) {
            System.out.println("Sin resultados");
        }
        autorsTemp.forEach(System.out::println);
    }

    private void mostrarAutores() {
        repositorioAutor.findAll().forEach(System.out::println);
    }

    private void mostrarLibrosRegistrados() {
        repositorioAutor.todosLosTitulos().forEach(System.out::println);
    }

    private void buscarLibroPorTitulo() throws IOException, InterruptedException {
        System.out.println("Escribe el nombre del libro que deseas buscar: ");
        String nombreTitulo = sc.nextLine();
        DatosConsulta consulta = buscarLibro(nombreTitulo);

        Optional<Titulo> titulo = consulta.resultados().stream()
                .map(t -> new Titulo(
                        t.titulo(),
                        t.idiomas(),
                        t.numeroDeDescargas(),
                        t.autores()
                )).findFirst();

        if (titulo.isPresent()) {
            Titulo tituloTemp = titulo.get();
            Autor autorTemp = tituloTemp.getAutors();
            if (repositorioAutor.buscarTituloPorNombre(tituloTemp.getTitulo()).isPresent()) {
                System.out.println("No se puede registrar este libro más de una vez");
            } else if (repositorioAutor.findByNombreContainsIgnoreCase(autorTemp.getNombre()).isPresent()) {
                tituloTemp.setAutors(repositorioAutor.findByNombreContainsIgnoreCase(autorTemp.getNombre()).get());
                tituloTemp.getAutors().addTitulo(tituloTemp);
                repositorioAutor.save(tituloTemp.getAutors());
                System.out.println(tituloTemp);
            } else {
                titulo.get().getAutors().addTitulo(titulo.get());
                repositorioAutor.save(titulo.get().getAutors());
                System.out.println(tituloTemp);
            }

        } else {
            System.out.println("Titulo no encontrado");
        }
    }

    private DatosConsulta buscarLibro(String titulo) throws IOException, InterruptedException {
        return conversor.obtenerDatos(new String(consumoApi.obtenerLibros(URL_BASE + SEARCH + titulo.replace(" ",
                "%20"))), DatosConsulta.class);
    }
}
