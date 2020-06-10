package br.com.climb.cdi;

public interface ManagerContext extends AutoCloseable {

    void disposeObjects();
    Object generateInstance(Class<?> aClass);

}
