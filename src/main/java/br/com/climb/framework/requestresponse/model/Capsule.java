package br.com.climb.framework.requestresponse.model;

import java.lang.reflect.Method;
import java.util.Arrays;

public class Capsule {

    private Method method;
    private Object[] args;

    public Capsule(Method method, Object[] args) {
        this.method = method;
        this.args = args;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return "Capsule{" +
                "method=" + method +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
