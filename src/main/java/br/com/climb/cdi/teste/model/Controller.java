package br.com.climb.cdi.teste.model;

import br.com.climb.cdi.Inject;

public class Controller {

    @Inject
    private Pessoa pessoa;

    @Inject
    private Carro carro;

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Carro getCarro() {
        return carro;
    }

    public void executar() {
        System.out.println(pessoa.getNome());
        System.out.println(carro.getNome());
    }

}
