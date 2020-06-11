package br.com.climb.test.model;


import br.com.climb.cdi.annotations.Component;

@Component
public class TeteInject {

    public String getTexto() {
        return "Chamou via injeção";
    }

}
