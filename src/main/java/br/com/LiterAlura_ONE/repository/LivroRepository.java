package br.com.LiterAlura_ONE.repository;

import br.com.LiterAlura_ONE.model.Idioma;
import br.com.LiterAlura_ONE.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByLinguagem(Idioma idioma);

    Optional<Livro> findByTitulo(String titulo);

    @Query("SELECT l FROM Livro l ORDER BY l.downNum DESC LIMIT 10")
    List<Livro> top10LivrosMaisBaixados();
}
