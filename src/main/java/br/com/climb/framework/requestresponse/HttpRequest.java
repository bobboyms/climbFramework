package br.com.climb.framework.requestresponse;

import java.io.BufferedReader;
import java.util.Map;

public class HttpRequest implements Request {

    private final String method;
    private final String pathInfo;
    private final Map<String, String[]> parameterMap;
    private final BufferedReader reader;
    private final String contentType;

    public HttpRequest(String method, String pathInfo,
                       String contentType,
                       Map<String, String[]> parameterMap,
                       BufferedReader reader) {
        this.method = method;
        this.pathInfo = pathInfo;
        this.parameterMap = parameterMap;
        this.reader = reader;
        this.contentType = contentType;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getPathInfo() {
        return pathInfo;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return parameterMap;
    }

    @Override
    public BufferedReader getReader() {
        return reader;
    }

    @Override
    public String getContentType() {
        return contentType;
    }
}
