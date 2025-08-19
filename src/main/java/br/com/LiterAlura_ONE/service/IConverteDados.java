package br.com.LiterAlura_ONE.service;

public interface IConverteDados {
    <T> T  obterDados(String json, Class<T> classe);
}
