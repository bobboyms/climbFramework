package br.com.climb.cdi;


import br.com.climb.cdi.teste.model.Controller;
import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.commons.configuration.FactoryConfigFile;
import br.com.climb.commons.execptions.ConfigFileException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ConfigFileException {

        ConfigFile configFile = new FactoryConfigFile().getConfigFile("framework.properties");

        ContainerInitializer containerInitializer = ContainerInitializer.newInstance(configFile);

        try(ManagerContext context = containerInitializer.createManager()) {
            Controller controller = (Controller) context.generateInstance(Controller.class);
//            controller.executar();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
