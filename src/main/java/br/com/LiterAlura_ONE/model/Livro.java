package br.com.LiterAlura_ONE.model;


import jakarta.persistence.*;


@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String titulo;
    @ManyToOne
    private Autor autor;
    @Enumerated(EnumType.STRING)
    private Idioma linguagem;
    private Integer downNum;

    public Livro(){}

    public Livro(DadosLivros dadosLivros){
        this.titulo = dadosLivros.titulo();
        this.linguagem = Idioma.fromString(dadosLivros.idiomas().get(0));
        this.downNum = dadosLivros.downNum();
    }

    @Override
    public String toString() {
        String nomeAutor = (autor != null) ? autor.getNome() : "Autor desconhecido";
        return String.format("********** Livro **********%nTitulo:" +
                " %s%nAutor: %s%nIdioma: %s%nNumero de Downloads:" +
                " %d%n***************************%n",titulo,nomeAutor,linguagem,downNum);
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Idioma getLinguagem() {
        return linguagem;
    }

    public void setLinguagem(Idioma linguagem) {
        this.linguagem = linguagem;
    }

    public Integer getDownNum() {
        return downNum;
    }

    public void setDownNum(Integer downNum) {
        this.downNum = downNum;
    }
}
