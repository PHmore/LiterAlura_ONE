package br.com.LiterAlura_ONE;

import br.com.LiterAlura_ONE.principal.Principal;
import br.com.LiterAlura_ONE.repository.AutorRepository;
import br.com.LiterAlura_ONE.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraOneApplication implements CommandLineRunner {


	@Autowired
	private LivroRepository repositoryLivro;
	@Autowired
	private AutorRepository repositoryAutor;


	public static void main(String[] args) {
		SpringApplication.run(LiterAluraOneApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositoryLivro, repositoryAutor);
		principal.mostrarMenu();

	}
}
