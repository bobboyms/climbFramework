package br.com.climb.cdi;


import br.com.climb.cdi.teste.model.Controller;

import java.io.IOException;

import static br.com.climb.cdi.teste.model.factory.ClimbCdiContainer.start;

public class Main {

    public static void main(String[] args) throws IOException {

        ContainerInitializer containerInitializer = ContainerInitializer.newInstance();

        try(ManagerContext context = containerInitializer.createManager()) {
            Controller controller = (Controller) context.generateInstance(Controller.class);
            controller.executar();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
