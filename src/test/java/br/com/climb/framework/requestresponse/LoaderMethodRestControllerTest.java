package br.com.climb.framework.requestresponse;

import br.com.climb.framework.annotations.RestController;
import br.com.climb.framework.execptions.NotFoundException;
import br.com.climb.framework.requestresponse.model.Capsule;
import br.com.climb.test.controller.TesteGetController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Set;

import static br.com.climb.framework.utils.ReflectionUtils.getAnnotedClass;
import static org.junit.jupiter.api.Assertions.*;

class LoaderMethodRestControllerTest {

    @Test
    void getBodyRequest() {
    }

    @Test
    void getNormalizedUrl() {
    }

    @Test
    void extractValueUrlRequestMapping() {
    }

    @Test
    void extractValueUrlRequestParam() {
    }

    @Test
    void getMethodForCall_1() throws NotFoundException, IOException {

        new LoaderClassRestController().storage(getAnnotedClass(RestController.class, "br.com."));

        LoaderMethodRestController loaderMethodRestController = new LoaderMethodRestController();

        Request request = new HttpRequest(
                "GET","/get/nome/thiago/peso/36/altura/177/idade/33/casado/true/",
                "",new HashMap<>(),
                new BufferedReader(new StringReader("")));


        Capsule capsule = loaderMethodRestController.getMethodForCall(request);
        Assertions.assertSame(TesteGetController.class, capsule.getMethod().getDeclaringClass());

    }

    @Test
    void getMethodForCall_2() throws NotFoundException, IOException {

        new LoaderClassRestController().storage(getAnnotedClass(RestController.class, "br.com."));

        LoaderMethodRestController loaderMethodRestController = new LoaderMethodRestController();

        Request request = new HttpRequest(
                "GET","/get/id/30/",
                "",new HashMap<>(),
                new BufferedReader(new StringReader("{\n" +
                        "    \"id\": 13065,\n" +
                        "    \"nome\": \"thiago\",\n" +
                        "    \"idade\": 33,\n" +
                        "    \"altura\": 177.0,\n" +
                        "    \"peso\": 36.0,\n" +
                        "    \"casado\": true\n" +
                        "}")));

        Capsule capsule = loaderMethodRestController.getMethodForCall(request);
        Assertions.assertSame(TesteGetController.class, capsule.getMethod().getDeclaringClass());
    }

    @Test
    void getMethodForCall_3() throws NotFoundException, IOException {

        new LoaderClassRestController().storage(getAnnotedClass(RestController.class, "br.com."));

        LoaderMethodRestController loaderMethodRestController = new LoaderMethodRestController();

        Request request = new HttpRequest(
                "GET","/get/id/30/",
                "",new HashMap<>(),
                new BufferedReader(new StringReader("{\n" +
                        "    \"id\": 13065,\n" +
                        "    \"nome\": \"thiago\",\n" +
                        "    \"idade\": 33,\n" +
                        "    \"altura\": 177.0,\n" +
                        "    \"peso\": 36.0,\n" +
                        "    \"casado\": true\n" +
                        "}")));

        Capsule capsule = loaderMethodRestController.getMethodForCall(request);
        Assertions.assertSame(TesteGetController.class, capsule.getMethod().getDeclaringClass());
    }
}