package br.com.climb.rpc.model;

public class RpcProcessedResponse {

    private String id;
    private String chanelName;
    private String result;
    private Integer statusCode;

    public void setId(String id) {
        this.id = id;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setChanelName(String chanelName) {
        this.chanelName = chanelName;
    }

    public String getId() {
        return id;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getResult() {
        return result;
    }

    public String getChanelName() {
        return chanelName;
    }

    @Override
    public String toString() {
        return "RpcProcessedResponse{" +
                "id='" + id + '\'' +
                ", chanelName='" + chanelName + '\'' +
                ", result='" + result + '\'' +
                ", statusCode=" + statusCode +
                '}';
    }
}
