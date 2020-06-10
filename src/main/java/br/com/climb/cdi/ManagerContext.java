package br.com.climb.cdi;

public interface ManagerContext extends AutoCloseable {

    Object generateInstance(Class<?> aClass);

}
