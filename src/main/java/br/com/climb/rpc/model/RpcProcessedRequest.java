package br.com.climb.rpc.model;

public class RpcProcessedRequest {

    private String id;
    private String chanelName;
    private String result;

    public void setId(String id) {
        this.id = id;
    }

    public void setChanelName(String chanelName) {
        this.chanelName = chanelName;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public String getChanelName() {
        return chanelName;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "RpcProcessedRequest{" +
                "id='" + id + '\'' +
                ", chanelName='" + chanelName + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
