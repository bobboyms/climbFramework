package br.com.climb.cdi.interceptor;

import java.lang.reflect.Method;

public interface Context {

    Object[] getArgs();

    void setArgs(Object[] args);

    Method getMethod();

    Object proceed() throws Throwable;

    Object getaClass();
}
