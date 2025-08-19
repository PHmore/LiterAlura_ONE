package br.com.LiterAlura_ONE.model;


import jakarta.persistence.*;

import java.util.List;




@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livro;

    public Autor(){}

    public Autor(DadosAutor dadosAutor){
        this.nome= dadosAutor.nome();
        this.anoNascimento = dadosAutor.anoNascimento();
        this.anoFalecimento = dadosAutor.anoFalecimento();
    }

    @Override
    public String toString() {
        StringBuilder livrosStr = new StringBuilder();
        livrosStr.append("Livros: ");

        for(int i = 0; i < livro.size() ; i++) {
            livrosStr.append(livro.get(i).getTitulo());
            if (i < livro.size() - 1 ){
                livrosStr.append(", ");
            }
        }
        return String.format("********** Autor **********%nNome:" +
                " %s%n%s%nAno de Nascimento: %s%nAno de Falecimento:" +
                " %s%n***************************%n",nome,livrosStr.toString(),anoNascimento,anoFalecimento);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nombre) {
        this.nome = nombre;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(Integer anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public List<Livro> getLivro() {
        return livro;
    }

    public void setLivro(List<Livro> livro) {
        this.livro = livro;
    }
}
