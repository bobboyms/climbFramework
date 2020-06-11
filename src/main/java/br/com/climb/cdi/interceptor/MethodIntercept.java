package br.com.climb.cdi.interceptor;

public interface MethodIntercept {

    Object interceptorMethod(Context ctx) throws Throwable;

}
