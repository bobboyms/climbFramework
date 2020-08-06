package br.com.climb.rpc.model;

import java.util.List;

public class RpcProducerRequest {

    private String ChanelName;
    private String ClassName;
    private String MethodName;
    private List<Arg> args;

    public String getMethodName() {
        return MethodName;
    }

    public List<Arg> getArgs() {
        return args;
    }

    public String getChanelName() {
        return ChanelName;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setArgs(List<Arg> args) {
        this.args = args;
    }

    public void setChanelName(String chanelName) {
        ChanelName = chanelName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public void setMethodName(String methodName) {
        MethodName = methodName;
    }

    @Override
    public String toString() {
        return "RpcProducerRequest{" +
                "ChanelName='" + ChanelName + '\'' +
                ", ClassName='" + ClassName + '\'' +
                ", MethodName='" + MethodName + '\'' +
                ", args=" + args +
                '}';
    }
}