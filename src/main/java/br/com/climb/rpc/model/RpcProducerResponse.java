package br.com.climb.rpc.model;

public class RpcProducerResponse {
    private String id;
    private Integer statusCode;
    private String message;

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "RpcProducerResponse{" +
                "id='" + id + '\'' +
                ", statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }
}