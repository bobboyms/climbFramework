package br.com.climb.commons.reqrespmodel;

import java.io.BufferedReader;
import java.io.Serializable;
import java.util.Map;

public interface Request extends Serializable {

    String getMethod();
    String getPathInfo();
    Map<String, String[]> getParameterMap();
    byte[] getReader();
    String getContentType();
    String getSessionId();

}
