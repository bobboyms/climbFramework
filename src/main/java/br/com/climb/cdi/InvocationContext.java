package br.com.climb.cdi;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class InvocationContext {

    private Method method;
    private Object[] args;
    private Object object;
    private MethodProxy proxy;

    public Class<?> getaClass() {
        return object.getClass().getSuperclass();
    }

    public InvocationContext(Object object, Method method, Object[] args, MethodProxy proxy) {
        this.object = object;
        this.method = method;
        this.args = args;
        this.proxy = proxy;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Method getMethod() {
        return method;
    }

    public Object procedd() throws Throwable {
//        Object instance = object.getClass().getSuperclass().newInstance();
        System.out.println(object);
        return proxy.invokeSuper(object, args);
    }

}
