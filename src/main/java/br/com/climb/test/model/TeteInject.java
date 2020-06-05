package br.com.climb.test.model;

import javax.inject.Named;

@Named
public class TeteInject {

    public String getTexto() {
        return "Chamou via injeção";
    }

}
