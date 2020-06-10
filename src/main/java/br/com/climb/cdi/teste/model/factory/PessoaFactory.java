package br.com.climb.cdi.teste.model.factory;

import br.com.climb.cdi.annotations.Factory;
import br.com.climb.cdi.annotations.Inject;
import br.com.climb.cdi.annotations.Produces;
import br.com.climb.cdi.annotations.Singleton;
import br.com.climb.cdi.teste.model.Pessoa;

@Factory
public class PessoaFactory {

    @Produces
    @Singleton
    public Pessoa getPessoa() {
        Pessoa pessoa = new Pessoa();
        System.out.println("chamou pra criar pessoa: " + pessoa);
        return pessoa;
    }

}
