package br.com.climb.cdi.disposes;

import br.com.climb.cdi.ContainerInitializer;
import br.com.climb.cdi.teste.model.factory.ArquivoTexto;
import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.commons.configuration.FactoryConfigFile;
import br.com.climb.framework.execptions.ConfigFileException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

class DisposesManagerTest {

    private DisposesManager disposesManager;

    public ConfigFile getConfigFile() throws IOException, ConfigFileException {
        return new FactoryConfigFile().getConfigFile("framework.properties");
    }

    @Test
    void create() throws IOException, ConfigFileException {
        disposesManager = new DisposesManager(ContainerInitializer.newInstance(getConfigFile()));
        Assertions.assertSame(false, Objects.isNull(disposesManager));
    }

    @Test
    void isDisposes() throws IOException, ConfigFileException {
        disposesManager = new DisposesManager(ContainerInitializer.newInstance(getConfigFile()));
        Boolean result = disposesManager.isDisposes(ArquivoTexto.class);
        Assertions.assertSame(true, result);
    }

    @Test
    void disposeObjects() {

    }

}