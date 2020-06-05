package br.com.climb.framework;

import br.com.climb.framework.annotations.RestController;
import br.com.climb.framework.requestresponse.LoaderClassRestController;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static br.com.climb.framework.utils.ReflectionUtils.getAnnotedClass;

class ControllerServletTest {

    @Test
    void doGet() throws IOException {

        Set<Class<?>> clazzs = getAnnotedClass(RestController.class, "br.com.");
        LoaderClassRestController loaderClassRestController = new LoaderClassRestController();
        loaderClassRestController.storage(clazzs);

//        final LoaderMethod loaderMethod = new LoaderMethodRestController();
//        final Capsule capsule = loaderMethod.getMethodForCall(req);
//        final Object result = callMethod(capsule.getMethod(), capsule.getArgs());

    }

    @Test
    void doPut() {
    }

    @Test
    void doDelete() {
    }

    @Test
    void doPost() {
    }


}