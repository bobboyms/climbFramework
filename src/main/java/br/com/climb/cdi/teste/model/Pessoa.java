package br.com.climb.cdi.teste.model;

import br.com.climb.cdi.annotations.Component;
import br.com.climb.cdi.annotations.Inject;
import br.com.climb.cdi.teste.model.interceptors.Logar;

@Component
public class Pessoa {

    public String getNome() {
        return "Thiago";
    }

    @Logar
    public String getTesteIntercept() {
        return "valor horiginal";
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

