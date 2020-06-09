package br.com.climb.cdi;


import br.com.climb.cdi.teste.model.Controller;

import java.io.IOException;

import static br.com.climb.cdi.ClimbCdiContainer.generateInstanceBase;

public class Main {

    public static void main(String[] args) throws IOException {

        Controller controller = (Controller) generateInstanceBase(Controller.class);
        System.out.println(controller.getPessoa().getEndereco().getNome());
        controller.executar();

    }

}