package br.com.climb.cdi.teste.model;

import br.com.climb.cdi.Component;

@Component
public class Endereco {

    public String getNome() {
        return "Rua 31 de março";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
