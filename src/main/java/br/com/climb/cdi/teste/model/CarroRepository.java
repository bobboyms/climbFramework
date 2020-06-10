package br.com.climb.cdi.teste.model;

import br.com.climb.cdi.annotations.Component;

@Component("carro")
public class CarroRepository implements Repository<Carro> {

    @Override
    public void salvar(Carro object) {
        System.out.println("Salvou carro : " + object);
    }
}
