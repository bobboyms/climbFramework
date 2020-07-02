package br.com.climb.framework.requestresponse;

import br.com.climb.commons.model.DiscoveryRequest;
import br.com.climb.commons.model.DiscoveryRequestObject;
import br.com.climb.commons.model.DiscoveryResponseObject;
import br.com.climb.commons.url.Methods;
import br.com.climb.commons.url.NormalizedUrl;
import br.com.climb.commons.url.NormalizedUrlManager;
import br.com.climb.commons.annotations.mapping.DeleteMapping;
import br.com.climb.commons.annotations.mapping.GetMapping;
import br.com.climb.commons.annotations.mapping.PutMapping;
import br.com.climb.commons.annotations.mapping.PostMapping;
import br.com.climb.commons.annotations.RequestMapping;
import br.com.climb.framework.messagesclient.annotations.MessageController;
import br.com.climb.framework.requestresponse.interfaces.Storage;

import static br.com.climb.commons.url.Methods.*;
import static br.com.climb.commons.utils.ReflectionUtils.*;
import static br.com.climb.framework.messagesclient.Methods.MESSAGE_CONTROLLERS;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class LoaderClassRestController implements Storage {

    private NormalizedUrl normalizedUrl;

    public LoaderClassRestController() {
        this.normalizedUrl = new NormalizedUrlManager();
    }

    @Override
    public Storage storageRestControllers(final Set<Class<?>> clazzs) {

        clazzs.parallelStream().forEach(clazz -> {

            RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);

            Arrays.stream(clazz.getMethods()).forEach(method -> {

                final GetMapping getMapping = method.getAnnotation(GetMapping.class);
                final PostMapping postMapping = method.getAnnotation(PostMapping.class);
                final PutMapping putMapping = method.getAnnotation(PutMapping.class);
                final DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);

                if (getMapping != null) {

                    System.out.println("GET: " + requestMapping.value() + getMapping.value());

                    if (requestMapping != null) {
                        storageGetMethod(requestMapping, method);
                    } else {
                        storageGetMethod(method);
                    }
                }

                if (postMapping != null) {

                    System.out.println("POST: " + requestMapping.value() + postMapping.value());

                    if (requestMapping != null) {
                        storagePostMethod(requestMapping, method);
                    } else {
                        storagePostMethod(method);
                    }
                }

                if (putMapping != null) {

                    System.out.println("PUT: " + requestMapping.value() + putMapping.value());

                    if (requestMapping != null) {
                        storagePutMethod(requestMapping, method);
                    } else {
                        storagePutMethod(method);
                    }
                }

                if (deleteMapping != null) {

                    System.out.println("DELETE: " + requestMapping.value() + deleteMapping.value());

                    if (requestMapping != null) {
                        storageDeleteMethod(requestMapping, method);
                    } else {
                        storageDeleteMethod(method);
                    }
                }

            });
        });

        return this;
    }

    @Override
    public void storageMessageControllers(Set<Class<?>> clazzs) {
        clazzs.stream().forEach(aClass -> {

            MessageController messageController = aClass.getDeclaredAnnotation(MessageController.class);
            MESSAGE_CONTROLLERS.put(messageController.topicName(), aClass);
            System.out.println("Message Controller: " + aClass);

        });
    }


    @Override
    public DiscoveryRequest generateDiscoveryRequest(String ipAddress, String port) {

        final DiscoveryRequestObject discoveryObject = new DiscoveryRequestObject();
        discoveryObject.setUrls(new HashMap<>());

        discoveryObject.setIpAddress(ipAddress);
        discoveryObject.setPort(port);

        final Set<String> urlsGet = new HashSet<>();
        Methods.GET.forEach((url, method) -> {
            urlsGet.add(url);
        });
        discoveryObject.getUrls().put("GET", urlsGet);

        final Set<String> urlsPost = new HashSet<>();
        Methods.POST.forEach((url, method) -> {
            urlsPost.add(url);
        });
        discoveryObject.getUrls().put("POST", urlsPost);

        final Set<String> urlsPut = new HashSet<>();
        Methods.PUT.forEach((url, method) -> {
            urlsPut.add(url);
        });
        discoveryObject.getUrls().put("PUT", urlsPut);

        final Set<String> urlsDelete = new HashSet<>();
        Methods.DELETE.forEach((url, method) -> {
            urlsDelete.add(url);
        });
        discoveryObject.getUrls().put("DELETE", urlsDelete);
        discoveryObject.setReservedWords(Methods.RESERVED_WORDS);

        return discoveryObject;
    }

    protected void storageGetMethod(RequestMapping requestMapping, Method method) {
        final GetMapping getMapping = method.getAnnotation(GetMapping.class);
        String value = requestMapping.value() + getMapping.value();
        storageGet(value, method);
    }

    protected void storageGetMethod(Method method) {

        final GetMapping getMapping = method.getAnnotation(GetMapping.class);
        String value = getMapping.value();
        storageGet(value, method);

    }

    protected void storagePostMethod(RequestMapping requestMapping, Method method) {

        final PostMapping getMapping = method.getAnnotation(PostMapping.class);
        String value = requestMapping.value() + getMapping.value();

        storagePost(value, method);
    }

    protected void storagePostMethod(Method method) {

        final PostMapping getMapping = method.getAnnotation(PostMapping.class);
        String value = getMapping.value();
        storagePost(value, method);

    }

    protected void storagePutMethod(RequestMapping requestMapping, Method method) {

        final PutMapping getMapping = method.getAnnotation(PutMapping.class);
        String value = requestMapping.value() + getMapping.value();

        storagePut(value, method);
    }

    protected void storagePutMethod(Method method) {

        final PutMapping getMapping = method.getAnnotation(PutMapping.class);
        String value = getMapping.value();
        storagePut(value, method);

    }

    protected void storageDeleteMethod(RequestMapping requestMapping, Method method) {

        final DeleteMapping getMapping = method.getAnnotation(DeleteMapping.class);
        String value = requestMapping.value() + getMapping.value();
        storageDelete(value, method);
    }

    protected void storageDeleteMethod(Method method) {

        final DeleteMapping getMapping = method.getAnnotation(DeleteMapping.class);
        String value = getMapping.value();
        storageDelete(value, method);

    }

    private void storageGet(String value, Method method) {
        value = normalizedUrl.getNormalizedUrl(value, method);
        GET.put(value, method);
        generateReservedWords(value);
    }

    private void storagePost(String value, Method method) {
        value = normalizedUrl.getNormalizedUrl(value, method);
        POST.put(value, method);
        generateReservedWords(value);
    }

    private void storagePut(String value, Method method) {
        value = normalizedUrl.getNormalizedUrl(value, method);
        PUT.put(value, method);
        generateReservedWords(value);
    }

    private void storageDelete(String value, Method method) {
        value = normalizedUrl.getNormalizedUrl(value, method);
        DELETE.put(value, method);
        generateReservedWords(value);
    }

    private void generateReservedWords(String value) {

        String[] splitedArray = value.split("/");

        for (long i = 1; i < splitedArray.length; i++) {

            final String word = splitedArray[(int) i];

            if (!isJavaType(word)) {
                Set<Long> position = RESERVED_WORDS.get(word);
                if (position == null) {
                    position = new HashSet<>();
                    RESERVED_WORDS.put(word, position);
                }
                position.add(i);
            }
        }
    }


}
