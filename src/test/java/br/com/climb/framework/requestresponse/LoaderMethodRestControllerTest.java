package br.com.climb.framework.requestresponse;

import br.com.climb.cdi.ManagerContext;
import br.com.climb.framework.annotations.RestController;
import br.com.climb.framework.execptions.NotFoundException;
import br.com.climb.framework.requestresponse.model.Capsule;
import br.com.climb.test.controller.java.*;
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
    void getMethodForCall_GET_SESSION() throws Exception {

        new LoaderClassRestController().storage(getAnnotedClass(RestController.class, "br.com."));

        LoaderMethodRestController loaderMethodRestController = new LoaderMethodRestController();

        Request request = new HttpRequest(
                "GET","/get/session/",
                "",new HashMap<>(),
                new BufferedReader(new StringReader("")));


        Capsule capsule = loaderMethodRestController.getMethodForCall(request);
        Assertions.assertSame(TesteGetControllerSession.class, capsule.getMethod().getDeclaringClass());

        Assertions.assertSame(0, capsule.getArgs().length);

        try(final ManagerContext context = containerInitializer.createManager()) {

            final Object instance = context.generateInstance(capsule.getMethod().getDeclaringClass(), "1234");
            final Long result = (Long) capsule.getMethod().invoke(instance, capsule.getArgs());

            Assertions.assertSame(0l, result.longValue());

        }

        try(final ManagerContext context = containerInitializer.createManager()) {

            final Object instance = context.generateInstance(capsule.getMethod().getDeclaringClass(), "1234");
            final Long result = (Long) capsule.getMethod().invoke(instance, capsule.getArgs());

            Assertions.assertSame(1l, result.longValue());

        }

        try(final ManagerContext context = containerInitializer.createManager()) {

            final Object instance = context.generateInstance(capsule.getMethod().getDeclaringClass(), "1234");
            final Long result = (Long) capsule.getMethod().invoke(instance, capsule.getArgs());

            Assertions.assertSame(2l, result.longValue());

        }

        try(final ManagerContext context = containerInitializer.createManager()) {

            final Object instance = context.generateInstance(capsule.getMethod().getDeclaringClass(), "1234");
            final Long result = (Long) capsule.getMethod().invoke(instance, capsule.getArgs());

            Assertions.assertSame(3l, result.longValue());

        }


    }

    @Test
    void getMethodForCall_GET_1() throws Exception {

        new LoaderClassRestController().storage(getAnnotedClass(RestController.class, "br.com."));

        LoaderMethodRestController loaderMethodRestController = new LoaderMethodRestController();

        Request request = new HttpRequest(
                "GET","/get/nome/thiago/peso/36/altura/177/idade/33/casado/true/",
                "",new HashMap<>(),
                new BufferedReader(new StringReader("")));


        Capsule capsule = loaderMethodRestController.getMethodForCall(request);
        Assertions.assertSame(TesteGetController.class, capsule.getMethod().getDeclaringClass());

        Assertions.assertSame(5, capsule.getArgs().length);
        Assertions.assertSame(true, capsule.getArgs()[0].equals("thiago"));
        Assertions.assertSame(true, capsule.getArgs()[1].equals(36d));
        Assertions.assertSame(true, capsule.getArgs()[2].equals(177f));
        Assertions.assertSame(true, capsule.getArgs()[3].equals(33l));
        Assertions.assertSame(true, capsule.getArgs()[4].equals(true));

        try(final ManagerContext context = containerInitializer.createManager()) {

            final Object instance = context.generateInstance(capsule.getMethod().getDeclaringClass());
            final Cliente result = (Cliente) capsule.getMethod().invoke(instance, capsule.getArgs());

            Assertions.assertSame(false, Objects.isNull(result));
            Assertions.assertSame(true, result.getNome().equals("thiago"));
            Assertions.assertSame(true, result.getPeso().equals(36d));
            Assertions.assertSame(true, result.getAltura().equals(177f));
            Assertions.assertSame(true, result.getIdade().equals(33l));
            Assertions.assertSame(true, result.getCasado().booleanValue());

        }


    }

    @Test
    void getMethodForCall_GET_2() throws NotFoundException, IOException {

        new LoaderClassRestController().storage(getAnnotedClass(RestController.class, "br.com."));

        LoaderMethodRestController loaderMethodRestController = new LoaderMethodRestController();

        Request request = new HttpRequest(
                "GET","/get/id/30/",
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
        Assertions.assertSame(TesteGetController.class, capsule.getMethod().getDeclaringClass());

        Assertions.assertSame(2, capsule.getArgs().length);
        Assertions.assertSame(true, capsule.getArgs()[0].equals(30l));
        Assertions.assertSame(Cliente.class, capsule.getArgs()[1].getClass());

        try(final ManagerContext context = containerInitializer.createManager()) {

            final Object instance = context.generateInstance(capsule.getMethod().getDeclaringClass());
            final Cliente result = (Cliente) capsule.getMethod().invoke(instance, capsule.getArgs());

            Assertions.assertSame(false, Objects.isNull(result));
            Assertions.assertSame(true, result.getNome().equals("thiago"));
            Assertions.assertSame(true, result.getPeso().equals(36d));
            Assertions.assertSame(true, result.getAltura().equals(177f));
            Assertions.assertSame(true, result.getIdade().equals(33l));
            Assertions.assertSame(true, result.getCasado().booleanValue());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void getMethodForCall_GET_3() throws NotFoundException, IOException {

        new LoaderClassRestController().storage(getAnnotedClass(RestController.class, "br.com."));

        LoaderMethodRestController loaderMethodRestController = new LoaderMethodRestController();

        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("id", new String[]{"30"});
        parameterMap.put("peso", new String[]{"150"});

        Request request = new HttpRequest(
                "GET","/get/pesoa/id/peso/",
                "",parameterMap,
                new BufferedReader(new StringReader("")));

        Capsule capsule = loaderMethodRestController.getMethodForCall(request);
        Assertions.assertSame(TesteGetController.class, capsule.getMethod().getDeclaringClass());

        Assertions.assertSame(2, capsule.getArgs().length);
        Assertions.assertSame(true, capsule.getArgs()[0].equals("30"));
        Assertions.assertSame(true, capsule.getArgs()[1].equals(150d));

    }

    @Test
    void getMethodForCall_GET_4() throws NotFoundException, IOException {

        new LoaderClassRestController().storage(getAnnotedClass(RestController.class, "br.com."));

        LoaderMethodRestController loaderMethodRestController = new LoaderMethodRestController();

        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("id", new String[]{"30"});
        parameterMap.put("peso", new String[]{"150"});

        Request request = new HttpRequest(
                "GET","/get/pesoa/id/peso/pessoa/",
                "application/json",parameterMap,
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

        Assertions.assertSame(3, capsule.getArgs().length);
        Assertions.assertSame(true, capsule.getArgs()[0].equals("30"));
        Assertions.assertSame(true, capsule.getArgs()[1].equals(150d));
        Assertions.assertSame(Cliente.class, capsule.getArgs()[2].getClass());

        try(final ManagerContext context = containerInitializer.createManager()) {

            final Object instance = context.generateInstance(capsule.getMethod().getDeclaringClass());
            final Cliente result = (Cliente) capsule.getMethod().invoke(instance, capsule.getArgs());

            Assertions.assertSame(false, Objects.isNull(result));
            Assertions.assertSame(true, result.getNome().equals("thiago"));
            Assertions.assertSame(true, result.getPeso().equals(36d));
            Assertions.assertSame(true, result.getAltura().equals(177f));
            Assertions.assertSame(true, result.getIdade().equals(33l));
            Assertions.assertSame(true, result.getCasado().booleanValue());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    void getMethodForCall_POST_1() throws NotFoundException, IOException {

        new LoaderClassRestController().storage(getAnnotedClass(RestController.class, "br.com."));

        LoaderMethodRestController loaderMethodRestController = new LoaderMethodRestController();

        Request request = new HttpRequest(
                "POST","/post/pessoa/20/",
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
        Assertions.assertSame(TestePostController.class, capsule.getMethod().getDeclaringClass());

        Assertions.assertSame(2, capsule.getArgs().length);
        Assertions.assertSame(true, capsule.getArgs()[0].equals(20l));
        Assertions.assertSame(Cliente.class, capsule.getArgs()[1].getClass());

        try(final ManagerContext context = containerInitializer.createManager()) {

            final Object instance = context.generateInstance(capsule.getMethod().getDeclaringClass());
            final Cliente result = (Cliente) capsule.getMethod().invoke(instance, capsule.getArgs());

            Assertions.assertSame(false, Objects.isNull(result));
            Assertions.assertSame(true, result.getNome().equals("thiago"));
            Assertions.assertSame(true, result.getPeso().equals(36d));
            Assertions.assertSame(true, result.getAltura().equals(177f));
            Assertions.assertSame(true, result.getIdade().equals(33l));
            Assertions.assertSame(true, result.getCasado().booleanValue());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    void getMethodForCall_POST_2() throws NotFoundException, IOException {

        new LoaderClassRestController().storage(getAnnotedClass(RestController.class, "br.com."));

        LoaderMethodRestController loaderMethodRestController = new LoaderMethodRestController();

        Request request = new HttpRequest(
                "POST","/post/pessoa/",
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
        Assertions.assertSame(TestePostController.class, capsule.getMethod().getDeclaringClass());

        Assertions.assertSame(1, capsule.getArgs().length);
        Assertions.assertSame(Cliente.class, capsule.getArgs()[0].getClass());

        try(final ManagerContext context = containerInitializer.createManager()) {

            final Object instance = context.generateInstance(capsule.getMethod().getDeclaringClass());
            final Cliente result = (Cliente) capsule.getMethod().invoke(instance, capsule.getArgs());

            Assertions.assertSame(false, Objects.isNull(result));
            Assertions.assertSame(true, result.getNome().equals("thiago"));
            Assertions.assertSame(true, result.getPeso().equals(36d));
            Assertions.assertSame(true, result.getAltura().equals(177f));
            Assertions.assertSame(true, result.getIdade().equals(33l));
            Assertions.assertSame(true, result.getCasado().booleanValue());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    void getMethodForCall_PUT_1() throws NotFoundException, IOException {

        new LoaderClassRestController().storage(getAnnotedClass(RestController.class, "br.com."));

        LoaderMethodRestController loaderMethodRestController = new LoaderMethodRestController();

        Request request = new HttpRequest(
                "PUT","/put/pessoa/34/",
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
        Assertions.assertSame(TestePutController.class, capsule.getMethod().getDeclaringClass());

        Assertions.assertSame(2, capsule.getArgs().length);
        Assertions.assertSame(true, capsule.getArgs()[0].equals(34l));
        Assertions.assertSame(Cliente.class, capsule.getArgs()[1].getClass());

        try(final ManagerContext context = containerInitializer.createManager()) {

            final Object instance = context.generateInstance(capsule.getMethod().getDeclaringClass());
            final Cliente result = (Cliente) capsule.getMethod().invoke(instance, capsule.getArgs());

            Assertions.assertSame(false, Objects.isNull(result));
            Assertions.assertSame(true, result.getNome().equals("thiago"));
            Assertions.assertSame(true, result.getPeso().equals(36d));
            Assertions.assertSame(true, result.getAltura().equals(177f));
            Assertions.assertSame(true, result.getIdade().equals(33l));
            Assertions.assertSame(true, result.getCasado().booleanValue());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    void getMethodForCall_PUT_2() throws NotFoundException, IOException {

        new LoaderClassRestController().storage(getAnnotedClass(RestController.class, "br.com."));

        LoaderMethodRestController loaderMethodRestController = new LoaderMethodRestController();

        Request request = new HttpRequest(
                "PUT","/put/pessoa/",
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
        Assertions.assertSame(TestePutController.class, capsule.getMethod().getDeclaringClass());

        Assertions.assertSame(1, capsule.getArgs().length);
        Assertions.assertSame(Cliente.class, capsule.getArgs()[0].getClass());

        try(final ManagerContext context = containerInitializer.createManager()) {

            final Object instance = context.generateInstance(capsule.getMethod().getDeclaringClass());
            final Cliente result = (Cliente) capsule.getMethod().invoke(instance, capsule.getArgs());

            Assertions.assertSame(false, Objects.isNull(result));
            Assertions.assertSame(true, result.getNome().equals("thiago"));
            Assertions.assertSame(true, result.getPeso().equals(36d));
            Assertions.assertSame(true, result.getAltura().equals(177f));
            Assertions.assertSame(true, result.getIdade().equals(33l));
            Assertions.assertSame(true, result.getCasado().booleanValue());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    void getMethodForCall_DELETE_1() throws NotFoundException, IOException {

        new LoaderClassRestController().storage(getAnnotedClass(RestController.class, "br.com."));

        LoaderMethodRestController loaderMethodRestController = new LoaderMethodRestController();

        Request request = new HttpRequest(
                "DELETE","/delete/pessoa/34/",
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
        Assertions.assertSame(TesteDeleteController.class, capsule.getMethod().getDeclaringClass());

        Assertions.assertSame(2, capsule.getArgs().length);
        Assertions.assertSame(true, capsule.getArgs()[0].equals(34l));
        Assertions.assertSame(Cliente.class, capsule.getArgs()[1].getClass());

        try(final ManagerContext context = containerInitializer.createManager()) {

            final Object instance = context.generateInstance(capsule.getMethod().getDeclaringClass());
            final Cliente result = (Cliente) capsule.getMethod().invoke(instance, capsule.getArgs());

            Assertions.assertSame(false, Objects.isNull(result));
            Assertions.assertSame(true, result.getNome().equals("thiago"));
            Assertions.assertSame(true, result.getPeso().equals(36d));
            Assertions.assertSame(true, result.getAltura().equals(177f));
            Assertions.assertSame(true, result.getIdade().equals(33l));
            Assertions.assertSame(true, result.getCasado().booleanValue());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    void getMethodForCall_DELETE_2() throws NotFoundException, IOException {

        new LoaderClassRestController().storage(getAnnotedClass(RestController.class, "br.com."));

        LoaderMethodRestController loaderMethodRestController = new LoaderMethodRestController();

        Request request = new HttpRequest(
                "DELETE","/delete/pessoa/",
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
        Assertions.assertSame(TesteDeleteController.class, capsule.getMethod().getDeclaringClass());

        Assertions.assertSame(1, capsule.getArgs().length);
        Assertions.assertSame(Cliente.class, capsule.getArgs()[0].getClass());

        try(final ManagerContext context = containerInitializer.createManager()) {

            final Object instance = context.generateInstance(capsule.getMethod().getDeclaringClass());
            final Cliente result = (Cliente) capsule.getMethod().invoke(instance, capsule.getArgs());

            Assertions.assertSame(false, Objects.isNull(result));
            Assertions.assertSame(true, result.getNome().equals("thiago"));
            Assertions.assertSame(true, result.getPeso().equals(36d));
            Assertions.assertSame(true, result.getAltura().equals(177f));
            Assertions.assertSame(true, result.getIdade().equals(33l));
            Assertions.assertSame(true, result.getCasado().booleanValue());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}

