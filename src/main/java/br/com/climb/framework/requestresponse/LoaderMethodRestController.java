package br.com.climb.framework.requestresponse;

import br.com.climb.framework.annotations.param.RequestBody;
import br.com.climb.framework.annotations.param.RequestParam;
import br.com.climb.framework.execptions.NotFoundException;
import br.com.climb.framework.requestresponse.interfaces.LoaderMethod;
import br.com.climb.framework.requestresponse.model.Capsule;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.climb.framework.requestresponse.Methods.*;
import static br.com.climb.framework.utils.ReflectionUtils.*;

public class LoaderMethodRestController implements LoaderMethod {

    private Object[] getCastValuesRequestMapping(Method method, List<String> values) {

        final Class[] types = method.getParameterTypes();
        final Object[] params = values.toArray();

        for (int i = 0; i < types.length; i++) {

            if (JAVA_TYPE_STRING.equals(types[i].toString())) {
                params[i] = values.get(i);
                continue;
            }

            if (JAVA_TYPE_INTEGER.equals(types[i].toString())) {
                params[i] = new Integer(values.get(i));
                continue;
            }

            if (JAVA_TYPE_LONG.equals(types[i].toString())) {
                params[i] = new Long(values.get(i));
                continue;
            }

            if (JAVA_TYPE_FLOAT.equals(types[i].toString())) {
                params[i] = new Float(values.get(i));
                continue;
            }

            if (JAVA_TYPE_DOUBLE.equals(types[i].toString())) {
                params[i] = new Double(values.get(i));
                continue;
            }

            if (JAVA_TYPE_BOOLEAN.equals(types[i].toString())) {
                params[i] = new Boolean(values.get(i));
                continue;
            }
        }

        return params;

    }

    protected Object[] getBodyRequest(Method method, HttpServletRequest request, Object[] arguments) throws IOException {

        List<Object> arg = new ArrayList<>(Arrays.asList(arguments));

        String body = request.getReader().lines().collect(Collectors.joining());

        if (body != null && body.trim().length() > 0 ) {

            if (request.getContentType().equals("application/json")) {

                Parameter[] parameters = method.getParameters();
                for (Parameter parameter : parameters) {
                    final RequestBody requestBody = parameter.getAnnotation(RequestBody.class);
                    if (requestBody != null) {
                        ObjectMapper mapper = new ObjectMapper();
                        Object result = mapper.readValue(body, parameter.getType());
                        arg.add(result);
                        break;
                    }
                }
            }
        }

        return arg.toArray();
    }

    protected String getNormalizedUrl(HttpServletRequest request) {

        final String[] arr = request.getPathInfo().split("/");

        final StringBuilder builder = new StringBuilder();
        builder.append("/");

        for (int position = 1; position < arr.length; position++) {

            final String word = arr[position].trim();

            if (isReservedWord(word, (long) position)) {
                builder.append(word+"/");
                continue;
            }

            if (isNumeric(word)) {
                builder.append(CLIMB_TYPE_NUMBER + "/");
                continue;
            }

            if (word.equals("true") || word.equals("false")) {
                builder.append(JAVA_TYPE_BOOLEAN+"/");
                continue;
            }

            builder.append(JAVA_TYPE_STRING+"/");
        }

        return builder.toString();

    }

    protected Object[] extractValueUrlRequestMapping(HttpServletRequest request, Method method) {
        final List<String> values = extractValueUrl(request);
        return getCastValuesRequestMapping(method, values);
    }

    protected Object[] extractValueUrlRequestParam(HttpServletRequest request, Method method) {

        final List<Object> valuesRequestParam = new ArrayList<>();

        request.getParameterMap().forEach((k, v) -> {

            Arrays.asList(method.getParameters()).forEach((param)->{

                RequestParam requestParam = param.getAnnotation(RequestParam.class);

                if (requestParam != null && requestParam.value().equals(k)) {

                    final String value = String.join(" ",Arrays.asList(v)).trim();

                    if (param.getType() == String.class) {
                        valuesRequestParam.add(value);
                    }

                    if (param.getType() == Integer.class || param.getType() == int.class) {
                        valuesRequestParam.add(new Integer(value));
                    }

                    if (param.getType() == Long.class || param.getType() == long.class) {
                        System.out.println(value);
                        valuesRequestParam.add(new Long(value));
                    }

                    if (param.getType() == Float.class || param.getType() == float.class) {
                        valuesRequestParam.add(new Float(value));
                    }

                    if (param.getType() == Double.class || param.getType() == double.class) {
                        valuesRequestParam.add(new Double(value));
                    }

                    if (param.getType() == Boolean.class || param.getType() == boolean.class) {
                        valuesRequestParam.add(new Boolean(value));
                    }
                }

            });
        });

        return valuesRequestParam.toArray();
    }

    private List<String> extractValueUrl(HttpServletRequest request) {

        final String[] arr = request.getPathInfo().split("/");
        final List<String> values = new ArrayList<>();

        for (int position = 1; position < arr.length; position++) {

            final String word = arr[position].trim();

            if (isReservedWord(word, (long) position)) {
                continue;
            }

            values.add(word);
        }

        return values;
    }

    @Override
    public Capsule getMethodForCall(HttpServletRequest request) throws NotFoundException, IOException {

        final String url = getNormalizedUrl(request);

        Method method = null;

        if (request.getMethod().equals("GET")) {
            method = GET.get(url);
        }

        if (request.getMethod().equals("POST")) {
            method = POST.get(url);
        }

        if (request.getMethod().equals("PUT")) {
            method = PUT.get(url);
        }

        if (request.getMethod().equals("DELETE")) {
            method = DELETE.get(url);
        }

        if (method == null) {
            throw new NotFoundException("method not found for url : " + request.getPathInfo());
        }

        Object[] args = extractValueUrlRequestParam(request, method);

        if (args.length == 0) {
            args = extractValueUrlRequestMapping(request, method);
        }

        return new Capsule(method, getBodyRequest(method, request, args));

    }
}

