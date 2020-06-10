package br.com.climb.cdi.teste.model;

import br.com.climb.cdi.annotations.Component;

@Component
public class Carro {

    public String getNome() {
        return "fusca";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
