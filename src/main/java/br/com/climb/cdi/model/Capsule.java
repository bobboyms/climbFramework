package br.com.climb.cdi.model;

import java.lang.reflect.Method;

public class Capsule {

    private Class classFactory;

    private Method method;

    private Boolean returnSingleton;

    public void setReturnSingleton(Boolean returnSingleton) {
        this.returnSingleton = returnSingleton;
    }

    public Boolean isReturnSingleton() {
        return returnSingleton;
    }

    public void setClassFactory(Class classFactory) {
        this.classFactory = classFactory;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class getClassFactory() {
        return classFactory;
    }

    public Method getMethod() {
        return method;
    }
}
