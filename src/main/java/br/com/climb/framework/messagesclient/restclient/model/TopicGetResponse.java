package br.com.climb.framework.messagesclient.restclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TopicGetResponse {

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Message")
    private String Message;

    @JsonProperty("StatusCode")
    private Integer StatusCode;

    public void setId(String id) {
        this.id = id;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public void setStatusCode(Integer statusCode) {
        StatusCode = statusCode;
    }

    public String getId() {
        return id;
    }

    public Integer getStatusCode() {
        return StatusCode;
    }

    public String getMessage() {
        return Message;
    }

    @Override
    public String toString() {
        return "TopicGetResponse{" +
                "id='" + id + '\'' +
                ", Message='" + Message + '\'' +
                ", StatusCode=" + StatusCode +
                '}';
    }
}
