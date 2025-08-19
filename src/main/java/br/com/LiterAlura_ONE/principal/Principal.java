package br.com.LiterAlura_ONE.principal;

import br.com.LiterAlura_ONE.service.ConsumoApi;
import br.com.LiterAlura_ONE.model.*;
import br.com.LiterAlura_ONE.repository.LivroRepository;
import br.com.LiterAlura_ONE.repository.AutorRepository;
import br.com.LiterAlura_ONE.service.ConverteDados;


import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConverteDados conversor = new ConverteDados();
    private LivroRepository repositoryLivro;
    private AutorRepository repositoryAutor;
    private List<Autor> autores;
    private List<Livro> livros;

    public Principal(LivroRepository repositoryLivro, AutorRepository repositoryAutor) {
        this.repositoryLivro = repositoryLivro;
        this.repositoryAutor = repositoryAutor;
    }

    public void mostrarMenu() {
        var opcao = -1;
        while (opcao != 0) {
            System.out.println("*********************************\n");
            var menu = """
                    1 - Buscar livros por título
                    2 - Mostrar livros registrados
                    3 - Mostrar autores registrados
                    4 - Autores vivos em determinado ano
                    5 - Buscar livros por idioma
                    6 - Top 10 lviros mais baixados
                    7 - Livro mais baixado e menos baixado 
                    
                    0 - Sair
                    
                    """;


            System.out.println(menu);
            while (!leitura.hasNextInt()) {
                System.out.println("Formato inválido, coloque um número do menu");
                leitura.nextLine();
            }
            opcao = leitura.nextInt();
            leitura.nextLine();
            switch (opcao) {
                case 1:
                    buscarLivro();
                    break;
                case 2:
                    mostrarLivros();
                    break;
                case 3:
                    mostrarAutores();
                    break;
                case 4:
                    autoresVivosPorAno();
                    break;
                case 5:
                    buscarLivroPorIdioma();
                    break;
                case 6:
                    top10LivrosMaisBaixados();
                    break;
                case 7:
                    rankingLivro();
                    break;
                case 0:
                    System.out.println("Finalizando a aplicação");
                    break;
                default:
                    System.out.printf("Opção inválida\n");
            }
        }
    }

    private DadosBuscados getBusca() {
        System.out.println("Escreva o nome do livro: ");
        var nomeLivro = leitura.nextLine();
        var json = consumoApi.obterDados(URL_BASE + nomeLivro.replace(" ", "%20"));
        DadosBuscados dados = conversor.obterDados(json, DadosBuscados.class);
        return dados;

    }

    private void buscarLivro() {
        DadosBuscados dadosBuscados = getBusca();
        if (dadosBuscados != null && !dadosBuscados.resultado().isEmpty()) {
            DadosLivros primeiroLivro = dadosBuscados.resultado().get(0);


            Livro livro = new Livro(primeiroLivro);
            System.out.println("***** Livro *****");
            System.out.println(livro);
            System.out.println("*****************");

            Optional<Livro> livroExiste = repositoryLivro.findByTitulo(livro.getTitulo());
            if (livroExiste.isPresent()){
                System.out.println("\nO livro já está registrado\n");
            }else {

                if (!primeiroLivro.autor().isEmpty()) {
                    DadosAutor autor = primeiroLivro.autor().get(0);
                    Autor autor1 = new Autor(autor);
                    Optional<Autor> autorOptional = repositoryAutor.findByNome(autor1.getNome());

                    if (autorOptional.isPresent()) {
                        Autor autorExiste = autorOptional.get();
                        livro.setAutor(autorExiste);
                        repositoryLivro.save(livro);
                    } else {
                        Autor autorNovo = repositoryAutor.save(autor1);
                        livro.setAutor(autorNovo);
                        repositoryLivro.save(livro);
                    }

                    Integer downNum = livro.getDownNum() != null ? livro.getDownNum() : 0;
                    System.out.println("********** Livro **********");
                    System.out.printf("Titulo: %s%nAutor: %s%nIdioma: %s%nNumero de downloads: %s%n",
                            livro.getTitulo(), autor1.getNome(), livro.getLinguagem(), livro.getDownNum());
                    System.out.println("***************************\n");
                } else {
                    System.out.println("Sem autor");
                }
            }
        } else {
            System.out.println("livro não encontrado");
        }
    }
    private void mostrarLivros() {
        livros = repositoryLivro.findAll();
        livros.stream()
                .forEach(System.out::println);
    }

    private void mostrarAutores() {
        autores = repositoryAutor.findAll();
        autores.stream()
                .forEach(System.out::println);
    }

    private void autoresVivosPorAno() {
        System.out.println("Coloque o ano do autor que deseja buscar: ");
        var ano = leitura.nextInt();
        autores = repositoryAutor.listaAutoresVivosPorAno(ano);
        autores.stream()
                .forEach(System.out::println);
    }

    private List<Livro> dadosBuscadosLinguagem(String idioma){
        var dato = Idioma.fromString(idioma);
        System.out.println("Lenguaje buscado: " + dato);

        List<Livro> livroPorIdioma = repositoryLivro.findByLinguagem(dato);
        return livroPorIdioma;
    }

    private void buscarLivroPorIdioma(){
        System.out.println("Selecione o idioma que deseja buscar: ");

        var opcao = -1;
        while (opcao != 0) {
            var opciones = """
                    1. en - Ingles
                    2. es - Español
                    3. fr - Francés
                    4. pt - Portugués
                    
                    0. Voltar as opções anteriores
                    """;
            System.out.println(opciones);
            while (!leitura.hasNextInt()) {
                System.out.println("Formato inválido, coloque um número do menu");
                leitura.nextLine();
            }
            opcao = leitura.nextInt();
            leitura.nextLine();
            switch (opcao) {
                case 1:
                    List<Livro> librosEnIngles = dadosBuscadosLinguagem("[en]");
                    librosEnIngles.forEach(System.out::println);
                    break;
                case 2:
                    List<Livro> librosEnEspanol = dadosBuscadosLinguagem("[es]");
                    librosEnEspanol.forEach(System.out::println);
                    break;
                case 3:
                    List<Livro> librosEnFrances = dadosBuscadosLinguagem("[fr]");
                    librosEnFrances.forEach(System.out::println);
                    break;
                case 4:
                    List<Livro> librosEnPortugues = dadosBuscadosLinguagem("[pt]");
                    librosEnPortugues.forEach(System.out::println);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Ningún idioma seleccionado");
            }
        }
    }

    private void top10LivrosMaisBaixados() {
        List<Livro> topLibros = repositoryLivro.top10LivrosMaisBaixados();
        topLibros.forEach(System.out::println);
    }

    private void rankingLivro() {
        livros = repositoryLivro.findAll();
        IntSummaryStatistics est = livros.stream()
                .filter(l -> l.getDownNum() > 0)
                .collect(Collectors.summarizingInt(Livro::getDownNum));

        Livro livroMaisBaixado = livros.stream()
                .filter(l -> l.getDownNum() == est.getMax())
                .findFirst()
                .orElse(null);

        Livro livroMenosBaixado = livros.stream()
                .filter(l -> l.getDownNum() == est.getMin())
                .findFirst()
                .orElse(null);
        System.out.println("******************************************************");
        System.out.printf("%nLivro mais baixado: %s%nNúmero de downloads: " +
                        "%d%n%nLivro menos baixado: %s%nNúmero de downloads: " +
                        "%d%n%n",livroMaisBaixado.getTitulo(),est.getMax(),
                livroMenosBaixado.getTitulo(),est.getMin());
        System.out.println("******************************************************");
    }

}






