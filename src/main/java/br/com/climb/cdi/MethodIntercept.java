package br.com.climb.cdi;

public interface MethodIntercept {

    Object interceptorMethod(InvocationContext ctx) throws Throwable;

}
