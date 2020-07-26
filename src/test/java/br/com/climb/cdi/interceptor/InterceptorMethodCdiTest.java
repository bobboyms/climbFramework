package br.com.climb.cdi.interceptor;

import br.com.climb.cdi.ContainerInitializer;
import br.com.climb.cdi.Initializer;
import br.com.climb.cdi.ManagerContext;
import br.com.climb.cdi.annotations.Component;
import br.com.climb.cdi.clazz.TypeOfClass;
import br.com.climb.cdi.clazz.TypeOfClassManager;
import br.com.climb.cdi.disposes.Disposes;
import br.com.climb.cdi.disposes.DisposesManager;
import br.com.climb.cdi.instances.InstancesManager;
import br.com.climb.cdi.teste.model.Controller;
import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.commons.configuration.FactoryConfigFile;
import br.com.climb.commons.execptions.ConfigFileException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class InterceptorMethodCdiTest {

    @Test
    void intercept() throws IOException, ConfigFileException {

        ConfigFile configFile = new FactoryConfigFile().getConfigFile("framework.properties");

        ContainerInitializer containerInitializer = ContainerInitializer.newInstance(configFile);

        try(ManagerContext context = containerInitializer.createManager()) {
            Controller controller = (Controller) context.generateInstance(Controller.class);
            Assertions.assertSame(true, controller.intercpt().equals("Valor interceptado"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}