package br.com.climb.commons.url;

import br.com.climb.commons.reqrespmodel.Request;

import java.lang.reflect.Method;

public interface NormalizedUrl {
    String getNormalizedUrl(String value, Method method);

    String getNormalizedUrl(Request request);
}
