package br.com.LiterAlura_ONE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraOneApplication implements CommandLineRunner {


	@Autowired
	private LivroRepository repositoryLibro;
	@Autowired
	private AutorRepository repositoryAutor;


	public static void main(String[] args) {
		SpringApplication.run(LiterAluraOneApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		com.alura.literalura.principal.Principal principal = new com.alura.literalura.principal.Principal(repositoryLibro, repositoryAutor);
		principal.muestraElMenu();

	}
}
