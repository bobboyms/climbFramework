package br.com.climb.cdi.interceptor;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class InvocationContext implements Context {

    private Method method;
    private Object[] args;
    private Object object;
    private MethodProxy proxy;

    public InvocationContext(Object object, Method method, Object[] args, MethodProxy proxy) {
        this.object = object;
        this.method = method;
        this.args = args;
        this.proxy = proxy;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }

    @Override
    public void setArgs(Object[] args) {
        this.args = args;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object proceed() throws Throwable {
        return proxy.invokeSuper(object, args);
    }

    @Override
    public Class<?> getaClass() {
        return object.getClass().getSuperclass();
    }

}
