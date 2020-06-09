package br.com.climb.cdi.teste.model;

import br.com.climb.cdi.Component;
import br.com.climb.cdi.Inject;

@Component
public class Pessoa {

    public String getNome() {
        return "Thiago";
    }

    @Inject
    private Endereco endereco;

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

