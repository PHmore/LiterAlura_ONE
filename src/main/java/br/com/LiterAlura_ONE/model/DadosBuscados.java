package br.com.LiterAlura_ONE.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosBuscados(

        @JsonAlias("count") Integer quantidade,
        @JsonAlias("results") List<DadosLivros> resultado
) {
}
