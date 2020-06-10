package br.com.climb.cdi.teste.model;

import br.com.climb.cdi.annotations.Component;

@Component("pessoa")
public class PessoaRepository implements Repository<Pessoa> {

    @Override
    public void salvar(Pessoa object) {
        System.out.println("Salvou pessoa : " + object);
    }
}
