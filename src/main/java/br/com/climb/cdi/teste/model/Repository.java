package br.com.climb.cdi.teste.model;

public interface Repository<T> {

    void salvar(T object);

}
