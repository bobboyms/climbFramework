package br.com.climb.commons.reqrespmodel;

import java.io.Serializable;
import java.util.Map;

public interface Response extends Serializable {

    byte[] getBody();
    Integer getStatus();
    String getCharacterEncoding();
    String getContentType();

}
