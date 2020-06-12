package br.com.climb.cdi.model;

import java.lang.reflect.Method;

public class Capsule {

    private Class classFactory;

    private Method method;

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
