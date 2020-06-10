package br.com.climb.cdi.teste.model;

import br.com.climb.cdi.annotations.Component;
import br.com.climb.cdi.teste.model.interceptors.Logar;

@Component("carro")
public class CarroRepository implements Repository<Carro> {


    @Override
    public void salvar(Carro object) {
        System.out.println("Salvou carro : " + object);
    }

    @Override
    public String processar(String valor) {
        return null;
    }
}
