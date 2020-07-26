package br.com.climb.cdi.teste.model;

import br.com.climb.cdi.annotations.Component;
import br.com.climb.cdi.annotations.Inject;
import br.com.climb.cdi.teste.model.interceptors.Logar;

@Component
public class Carro {

    @Inject
    private Endereco endereco;

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Logar
    public String inteceptou() {
        return "Aqui é um valor horiginal";
    }

    public Endereco getEndereco() {
        return endereco;
    }

    @Logar
    public String getNome() {
        return "fusca";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
