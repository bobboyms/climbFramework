package br.com.climb.cdi.teste.model;

import br.com.climb.cdi.Component;

@Component
public class PessoaRepository implements Repository {
    @Override
    public void salvar(Object object) {
        System.out.println("Salvou : " + object);
    }
}
