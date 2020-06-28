package br.com.climb.commons.reqrespmodel;

import java.util.Arrays;
import java.util.Map;

public class ObjectResponse implements Response {

    private String contentType;
    private String characterEncoding;
    private Integer status;
    private byte[] body;

    public void setContentType(String type) {
        this.contentType = type;
    }

    public void setCharacterEncoding(String charset) {
        this.characterEncoding = charset;
    }

    public void setStatus(int sc) {
        this.status = sc;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }

    public Integer getStatus() {
        return status;
    }

    public String getCharacterEncoding() {
        return characterEncoding;
    }

    public String getContentType() {
        return contentType;
    }

    @Override
    public String toString() {
        return "ObjectResponse{" +
                "contentType='" + contentType + '\'' +
                ", characterEncoding='" + characterEncoding + '\'' +
                ", status=" + status +
                ", body=" + Arrays.toString(body) +
                '}';
    }
}
