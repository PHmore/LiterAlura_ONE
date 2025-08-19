package br.com.LiterAlura_ONE.model;

public enum Idioma {
    en("[en]", "Ingles"),
    es("[es]", "Español"),
    fr("[fr]", "Frances"),
    pt("[pt]", "Portugues");

    private String idiomaGutendex;
    private String idiomaPortugues;

    Idioma(String idiomaGutendex, String idiomaEspanol){
        this.idiomaGutendex = idiomaGutendex;
        this.idiomaPortugues = idiomaEspanol;

    }

    public static Idioma fromString(String text){
        for (Idioma idioma : Idioma.values()){
            if (idioma.idiomaGutendex.equalsIgnoreCase(text)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("Nenhum idioma encontrado: " + text);
    }

    public static Idioma fromEspanol(String text){
        for (Idioma idioma : Idioma.values()){
            if (idioma.idiomaPortugues.equalsIgnoreCase(text)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("Nenhum idioma encontrado: " + text);
    }

    public String getIdiomaGutendex() {
        return idiomaGutendex;
    }

    public String getIdiomaEspanol() {
        return idiomaPortugues;
    }

}
