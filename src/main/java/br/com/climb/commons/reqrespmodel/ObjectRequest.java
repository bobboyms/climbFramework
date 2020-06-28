package br.com.climb.commons.reqrespmodel;

import java.util.Arrays;
import java.util.Map;

public class ObjectRequest implements Request {

    private final String method;
    private final String pathInfo;
    private final Map<String, String[]> parameterMap;
    private final byte[] reader;
    private final String contentType;

    public ObjectRequest(String method, String pathInfo,
                         String contentType,
                         Map<String, String[]> parameterMap,
                         byte[] reader) {
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
    public byte[] getReader() {
        return reader;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public String toString() {
        return "ObjectRequest{" +
                "method='" + method + '\'' +
                ", pathInfo='" + pathInfo + '\'' +
                ", parameterMap=" + parameterMap +
                ", reader=" + Arrays.toString(reader) +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
