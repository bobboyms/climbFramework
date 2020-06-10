package br.com.climb.cdi.teste.model;

import br.com.climb.cdi.annotations.Component;
import br.com.climb.cdi.teste.model.interceptors.Logar;

@Component("pessoa")
public class PessoaRepository implements Repository<Pessoa> {


    @Logar
    @Override
    public void salvar(Pessoa object) {
        System.out.println("Salvou pessoa : " + object);
    }

//    @Logar
    public String processar(String valor) {
        return valor;
    }
}
