package br.com.climb.framework.requestresponse;

import br.com.climb.cdi.ManagerContext;
import br.com.climb.framework.annotations.RestController;
import br.com.climb.framework.execptions.NotFoundException;
import br.com.climb.framework.requestresponse.model.Capsule;
import br.com.climb.test.controller.java.*;
import br.com.climb.test.controller.kotlin.TesteGetControllerKt;
import br.com.climb.test.model.Cliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static br.com.climb.framework.JettyServer.containerInitializer;
import static br.com.climb.framework.utils.ReflectionUtils.getAnnotedClass;

class LoaderMethodRestControllerKotlinTest {


    @Test
    void getMethodForCall_GET_1() throws Exception {

        new LoaderClassRestController().storage(getAnnotedClass(RestController.class, "br.com."));

        LoaderMethodRestController loaderMethodRestController = new LoaderMethodRestController();

        Request request = new HttpRequest(
                "GET","/get/kotlin/id/30/idade/33/nome/taliba da silva/altura/177/peso/79/casado/true/",
                "application/json",new HashMap<>(),
                new BufferedReader(new StringReader("{\n" +
                        "    \"id\": 13065,\n" +
                        "    \"nome\": \"thiago\",\n" +
                        "    \"idade\": 33,\n" +
                        "    \"altura\": 177.0,\n" +
                        "    \"peso\": 36.0,\n" +
                        "    \"casado\": true\n" +
                        "}")));

        Capsule capsule = loaderMethodRestController.getMethodForCall(request);
        Assertions.assertSame(TesteGetControllerKt.class, capsule.getMethod().getDeclaringClass());

        try(final ManagerContext context = containerInitializer.createManager()) {

            final Object instance = context.generateInstance(capsule.getMethod().getDeclaringClass());
            final Cliente result = (Cliente) capsule.getMethod().invoke(instance, capsule.getArgs());

            Assertions.assertSame(false, Objects.isNull(result));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}

