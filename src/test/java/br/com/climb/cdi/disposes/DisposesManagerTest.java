package br.com.climb.cdi.disposes;

import br.com.climb.cdi.ContainerInitializer;
import br.com.climb.cdi.teste.model.factory.ArquivoTexto;
import br.com.climb.cdi.teste.model.factory.ArquivoTextoFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class DisposesManagerTest {

    private DisposesManager disposesManager;

    @Test
    void create() {
        disposesManager = new DisposesManager(ContainerInitializer.newInstance());
        Assertions.assertSame(false, Objects.isNull(disposesManager));
    }

    @Test
    void isDisposes() {
        disposesManager = new DisposesManager(ContainerInitializer.newInstance());
        Boolean result = disposesManager.isDisposes(ArquivoTexto.class);
        Assertions.assertSame(true, result);
    }

    @Test
    void disposeObjects() {

    }

}