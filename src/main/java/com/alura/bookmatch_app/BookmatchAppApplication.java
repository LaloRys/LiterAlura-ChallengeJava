package com.alura.bookmatch_app;

import com.alura.bookmatch_app.principal.Principal;
import com.alura.bookmatch_app.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BookmatchAppApplication implements CommandLineRunner {
	//Injecion de dependencias
	@Autowired
	private LibroRepository libroRepository;

	public static void main(String[] args) {

		SpringApplication.run(BookmatchAppApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		Principal p =  new Principal(libroRepository);
		p.iniciarPrograma();
	}
}
