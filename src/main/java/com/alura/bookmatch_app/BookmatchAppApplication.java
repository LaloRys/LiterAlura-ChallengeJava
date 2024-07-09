package com.alura.bookmatch_app;

import com.alura.bookmatch_app.principal.Principal;
import com.alura.bookmatch_app.repository.RepositorioAutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BookmatchAppApplication implements CommandLineRunner {
	//Injecion de dependencias
	@Autowired
	private RepositorioAutor repositorioAutor;

	public static void main(String[] args) {

		SpringApplication.run(BookmatchAppApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		Principal p =  new Principal(repositorioAutor);
		p.iniciarPrograma();
	}
}
