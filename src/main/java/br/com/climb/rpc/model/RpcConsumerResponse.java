package br.com.climb.rpc.model;

import java.util.List;

public class RpcConsumerResponse {

    private String id;
    private Integer statusCode;
    private String className;
    private String methodName;
    private List<Arg> args;

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setArgs(List<Arg> args) {
        this.args = args;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<Arg> getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return "RpcConsumerResponse{" +
                "id='" + id + '\'' +
                ", statusCode=" + statusCode +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", args=" + args +
                '}';
    }
}