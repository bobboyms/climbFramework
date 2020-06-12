package br.com.climb.framework.requestresponse;

import java.io.BufferedReader;
import java.util.Map;

public interface Request {

    String getMethod();
    String getPathInfo();
    Map<String, String[]> getParameterMap();
    BufferedReader getReader();
    String getContentType();

}
